package com.qiuhongtao.service;

import com.qiuhongtao.param.PageParam;


public interface OrderService {

    /**
     * 分页查询订单数据
     * @param pageParam
     * @return
     */
    Object list(PageParam pageParam);
}
