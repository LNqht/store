package com.qiuhongtao.controller;

import com.qiuhongtao.service.CartService;
import com.qiuhongtao.param.CartParam;
import com.qiuhongtao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("save")
    public R save(@RequestBody CartParam cartParam){

        return cartService.save(cartParam);
    }

    @PostMapping("list")
    public R list(@RequestBody CartParam cartParam){

        return cartService.list(cartParam);
    }


    @PostMapping("update")
    public R update(@RequestBody CartParam cartParam){

        return cartService.update(cartParam);
    }


    @PostMapping("remove")
    public R remove(@RequestBody CartParam cartParam){

        return cartService.remove(cartParam);
    }


    /**
     * 检查是否存在对应商品
     * @param productId
     * @return
     */
    @PostMapping("check")
    public R check(@RequestBody Integer productId){

        return cartService.check(productId);
    }

}
