package com.qiuhongtao.collect.service;

import com.qiuhongtao.param.CollectParam;


public interface CollectService {

    /**
     * 收藏保存服务
     * @param collectParam
     * @return
     */
    Object save(CollectParam collectParam);

    /**
     * 查询收藏列表
     * @param collectParam
     * @return
     */
    Object list(CollectParam collectParam);

    /**
     * 删除收藏业务
     * @param collectParam
     * @return
     */
    Object remove(CollectParam collectParam);

    /**
     * 商品商品id对应的收藏信息
     * @param productId
     * @return
     */
    Object removeByPid(Integer productId);
}
