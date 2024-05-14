package com.qiuhongtao.order.service;

import com.alipay.api.AlipayApiException;
import com.qiuhongtao.param.OrderParam;
import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {

    /**
     * 订单保存业务
     * @param orderParam
     * @return
     */
    Object save(OrderParam orderParam) throws AlipayApiException;

    /**
     * 订单数据查询业务
     * @param orderParam
     * @return
     */
    Object list(OrderParam orderParam);

    /**
     * 检查订单是否包含要删除的商品
     * @param productId
     * @return
     */
    Object check(Integer productId);

    /**
     * 分页查询订单数据
     * @param pageParam
     * @return
     */
    Object adminList(PageParam pageParam);

}
