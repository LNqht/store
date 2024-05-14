package com.qiuhongtao.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.qiuhongtao.clients.ProductClient;
import com.qiuhongtao.order.mapper.OrderMapper;
import com.qiuhongtao.order.service.OrderService;
import com.qiuhongtao.param.OrderParam;
import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.param.ProductIdsParam;
import com.qiuhongtao.param.ProductNumberParam;
import com.qiuhongtao.pojo.Order;
import com.qiuhongtao.pojo.Product;
import com.qiuhongtao.utils.R;
import com.qiuhongtao.vo.AdminOrderVo;
import com.qiuhongtao.vo.CartVo;
import com.qiuhongtao.vo.OrderVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OrderServiceImpl  extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private Environment config;

    /**
     * 消息队列发送
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 订单保存业务
     * 库存和购物车使用mq异步,避免分布式事务!
     * @param orderParam
     * @return
     */
    @Transactional //添加事务
    @Override
    public String save(OrderParam orderParam) throws AlipayApiException {

        //修改清空购物车的参数
        List<Integer> cartIds = new ArrayList<>();
        //修改批量插入数据库的参数
        List<Order>  orderList = new ArrayList<>();
        //商品修改库存参数集合
        List<ProductNumberParam>  productNumberParamList  =
                new ArrayList<>();

        Integer userId = orderParam.getUserId();
        List<CartVo> products = orderParam.getProducts();
        //封装order实体类集合
        //统一生成订单编号和创建时间
        //使用时间戳 + 做订单编号和事件
        long ctime = System.currentTimeMillis();

        for (CartVo cartVo : products) {
            cartIds.add(cartVo.getId()); //进行购物车订单保存
            //订单信息保存
            Order order = new Order();
            order.setOrderId(ctime);
            order.setUserId(userId);
            order.setOrderTime(ctime);
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            orderList.add(order); //添加用户信息

            //修改信息存储
            ProductNumberParam productNumberParam = new ProductNumberParam();
            productNumberParam.setProductId(cartVo.getProductID());
            productNumberParam.setProductNum(cartVo.getNum());
            productNumberParamList.add(productNumberParam); //添加集合
        }

        //批量数据插入
        this.saveBatch(orderList); //批量保存

        //修改商品库存 [product-service] [异步通知]
        /**
         *  交换机: topic.ex
         *  routingkey: sub.number
         *  消息: 商品id和减库存数据集合
         */
        rabbitTemplate.convertAndSend("topic.ex","sub.number",productNumberParamList);
        //清空对应购物车数据即可 [注意: 不是清空用户所有的购物车数据] [cart-service] [异步通知]
        /**
         * 交换机:topic.ex
         * routingkey: clear.cart
         * 消息: 要清空的购物车id集合
         */
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);

        //调用支付宝接口
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        //request.setNotifyUrl("");
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(config.getProperty("alipay.return-url"));
        /******必传参数******/
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", ctime);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", orderParam.getTotalPrice());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", ctime);
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request,"POST");
        // 如果需要返回GET请求，请使用
        // AlipayTradePagePayResponse response = alipayClient.pageExecute(request,"GET");
        String pageRedirectionData = response.getBody();
        System.out.println(pageRedirectionData);

        if(response.isSuccess()){
            System.out.println("调用成功");
            return response.getBody();
        } else {
            System.out.println("调用失败");
            throw new RuntimeException("创建支付交易失败");
        }
    }

    /**
     * 订单数据查询业务
     *
     * @param orderParam
     * @return
     */
    @Override
    public Object list(OrderParam orderParam) {

        Integer userId = orderParam.getUserId();
        //查询用户对应的全部订单数据
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id",userId);
        List<Order> orderList = this.list(orderQueryWrapper);

        Set<Integer> productIds = new HashSet<>();
        for (Order order : orderList) {
            productIds.add(order.getProductId());
        }


        //数据按订单分组
        Map<Long, List<Order>> listMap = orderList.stream().
                collect(Collectors.groupingBy(Order::getOrderId));

        //结果集封装,返回即可
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(new ArrayList<>(productIds));

        List<Product> productList = productClient.ids(productIdsParam);
        //商品数据
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //结果封装
        List<List<OrderVo>> result = new ArrayList<>();

        for (List<Order> orders : listMap.values()) {
            List<OrderVo> orderVos = new ArrayList<>();
            for (Order order : orders) {
                //返回vo数据封装
                OrderVo orderVo = new OrderVo();
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVo.setId(order.getId());
                orderVo.setOrderId(order.getOrderId());
                orderVo.setOrderTime(order.getOrderTime());
                orderVo.setProductNum(order.getProductNum());
                orderVo.setProductId(order.getProductId());
                orderVo.setProductPrice(order.getProductPrice());
                orderVo.setUserId(order.getUserId());
                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }

        R ok = R.ok(result);
        log.info("OrderServiceImpl.list业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 检查订单是否包含要删除的商品
     *
     * @param productId
     * @return
     */
    @Override
    public Object check(Integer productId) {

        QueryWrapper<Order> queryWrapper
                  = new QueryWrapper<>();

        queryWrapper.eq("product_id",productId);

        Long total = baseMapper.selectCount(queryWrapper);

        if (total == 0){

            return R.ok("订单中不存在要删除的商品!");
        }

        return R.fail("订单中存在要删除的商品,删除失败!");
    }

    /**
     * 分页查询订单数据
     *
     * @param pageParam
     * @return
     */
    @Override
    public Object adminList(PageParam pageParam) {

        int offset = (pageParam.getCurrentPage()-1)*pageParam.getPageSize();
        int number = pageParam.getCurrentPage() * pageParam.getPageSize();

        //查询数量
        //Long total = orderMapper.selectCount(null);
        //自定义查询
        List<AdminOrderVo> adminOrderVoList = orderMapper.selectAdminOrders(offset,number);

        Long total = Long.valueOf(orderMapper.selectAdminOrders(0,100000).size());

        return R.ok("查询成功",adminOrderVoList,total);
    }
}
