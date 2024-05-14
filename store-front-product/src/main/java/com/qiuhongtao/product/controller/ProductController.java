package com.qiuhongtao.product.controller;

import com.qiuhongtao.param.ProductIdsParam;
import com.qiuhongtao.param.ProductParamsSearch;
import com.qiuhongtao.param.ProductParamsString;
import com.qiuhongtao.param.ProductSaveParam;
import com.qiuhongtao.pojo.Product;
import com.qiuhongtao.product.param.ProductParamInteger;
import com.qiuhongtao.product.service.ProductService;
import com.qiuhongtao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;


    /**
     * 查询全部商品信息,供search服务更新
     * @return
     */
    @GetMapping("list")
    public List<Product> list(){

        return productService.list();
    }


    /**
     * 供收藏服务使用,根据传入的id,查询商品集合!
     * @return
     */
    @PostMapping("ids")
    public List<Product> list(@RequestBody ProductIdsParam productIdsParam){

        return productService.ids(productIdsParam);
    }



    @PostMapping("promo")
    public Object indexPromo(@RequestBody Map<String,String> params){
        String categoryName = params.get("categoryName");
        return productService.promo(categoryName);
    }


    @PostMapping("hots")
    public Object indexHots(@RequestBody ProductParamsString productParamsString){

        return productService.hots(productParamsString);
    }

    @PostMapping("category/list")
    public  Object categoryList(){

        return productService.clist();
    }


    /**
     * 类别查询
     * @param productParamInteger
     * @return
     */
    @PostMapping("bycategory")
    public Object byCategory(@RequestBody ProductParamInteger productParamInteger){

        return productService.byCategory(productParamInteger);
    }

    /**
     * 查询全部商品,可以复用业务!
     * @param productParamInteger
     * @return
     */
    @PostMapping("all")
    public Object all(@RequestBody ProductParamInteger productParamInteger){

        return productService.all(productParamInteger);
    }


    @PostMapping("detail")
    public Object detail(@RequestBody Map<String,Integer> param){
        Integer productID = param.get("productID");
        return productService.detail(productID);
    }

    @PostMapping("pictures")
    public Object productPictures(@RequestBody Map<String,Integer> param){
        Integer productID = param.get("productID");
        return productService.pictures(productID);
    }


    @PostMapping("search")
    public Object search(@RequestBody ProductParamsSearch productParamsSearch){

        return productService.search(productParamsSearch);
    }

    /**
     * 类别服务调用管理调用
     */
    @PostMapping("/category/count")
    public Long categoryCount(@RequestBody Integer categoryId){

        return productService.categoryCount(categoryId);
    }


    @PostMapping("save")
    public R save(@RequestBody ProductSaveParam productSaveParam){
        return productService.save(productSaveParam);
    }


    @PostMapping("update")
    public R update(@RequestBody Product product){
        return productService.update(product);
    }

    @PostMapping("remove")
    public R remove(@RequestBody Integer productId){

        return productService.remove(productId);
    }

}
