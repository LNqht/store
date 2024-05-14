package com.qiuhongtao.clients;

import com.qiuhongtao.param.ProductIdsParam;
import com.qiuhongtao.param.ProductParamsSearch;
import com.qiuhongtao.param.ProductSaveParam;
import com.qiuhongtao.pojo.Product;
import com.qiuhongtao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "product-service")
public interface ProductClient {

    /**
     * 商品全部数据调用
     * @return
     */
    @GetMapping("/product/list")
    List<Product> list();

    /**
     * 收藏模块调用
     * @param productIdsParam
     * @return
     */
    @PostMapping("/product/ids")
    List<Product> ids(@RequestBody ProductIdsParam productIdsParam);

    @PostMapping("/product/category/count")
    long count(@RequestBody  Integer categoryId);


    /**
     * 后台管理调用!
     * @param paramsSearch
     * @return
     */
    @PostMapping("/product/search")
    R searchPage(@RequestBody ProductParamsSearch paramsSearch);

    @PostMapping("/product/save")
    R save(@RequestBody ProductSaveParam saveParam);

    @PostMapping("/product/update")
    R update(@RequestBody  Product product);

    @PostMapping("product/remove")
    R remove(@RequestBody Integer productId);
}
