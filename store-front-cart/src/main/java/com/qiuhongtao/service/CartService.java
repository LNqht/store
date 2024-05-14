package com.qiuhongtao.service;

import com.qiuhongtao.param.CartParam;
import com.qiuhongtao.pojo.Cart;
import com.qiuhongtao.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;


public interface CartService  extends IService<Cart> {

    /**
     * 添加购物车
     * @param cartParam
     * @return
     */
    R save(CartParam cartParam);

    /**
     * 查询购物车数据集合
     * @param cartParam
     * @return
     */
    R list(CartParam cartParam);

    /**
     * 修改购物车数量
     * @param cartParam
     * @return
     */
    R update(CartParam cartParam);

    /**
     * 移除购物车数据
     * @param cartParam
     * @return
     */
    R remove(CartParam cartParam);

    /**
     * 检查商品是否存在
     * @param productId
     * @return
     */
    R check(Integer productId);
}
