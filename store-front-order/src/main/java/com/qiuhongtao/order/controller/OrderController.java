package com.qiuhongtao.order.controller;

import com.alipay.api.AlipayApiException;
import com.qiuhongtao.order.service.OrderService;
import com.qiuhongtao.param.OrderParam;
import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * 订单数据保存
     * @param orderParam
     * @return
     */
    @PostMapping("save")
    public Object save(@RequestBody OrderParam orderParam) throws AlipayApiException {

        //支付宝开放平台接受 request 请求对象后
        //会为开发者生成一个html形式的form表单，包含自动提交的脚本
        String formStr = (String) orderService.save(orderParam);

        return R.ok("",formStr);
    }


    /**
     * 订单集合查询,注意,按照类别查询!
     * @param orderParam
     * @return
     */
    @PostMapping("/list")
    public Object list(@RequestBody OrderParam orderParam){

        return orderService.list(orderParam);
    }


    /**
     * 检查订单是否包含要删除的商品
     * @param productId
     * @return
     */
    @PostMapping("/check")
    public  Object check(@RequestBody Integer productId){
        return orderService.check(productId);
    }


    @PostMapping("/admin/list")
    public Object adminList(@RequestBody PageParam pageParam){

        return orderService.adminList(pageParam);
    }
}
