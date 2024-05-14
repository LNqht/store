package com.qiuhongtao.clients;

import com.qiuhongtao.param.ProductParamsSearch;
import com.qiuhongtao.pojo.Product;
import com.qiuhongtao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "search-service")
public interface SearchClient {

    /**
     * 搜索服务 商品查询
     * @param productParamsSearch
     * @return
     */
    @PostMapping("/search/product")
    R search(@RequestBody ProductParamsSearch productParamsSearch);

    @PostMapping("/search/save")
    R saveOrUpdate(@RequestBody Product product);

    @PostMapping("/search/remove")
    R remove(@RequestBody Integer productId);


}
