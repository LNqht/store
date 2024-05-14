package com.qiuhongtao.search.service;

import com.qiuhongtao.param.ProductParamsSearch;
import com.qiuhongtao.pojo.Product;
import com.qiuhongtao.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;


public interface SearchService {

    /**
     * 商品搜索
     * @param productParamsSearch
     * @return
     */
    R search(ProductParamsSearch productParamsSearch) throws JsonProcessingException;

    R save(Product product) throws IOException;

    R remove(Integer productId) throws IOException;
}
