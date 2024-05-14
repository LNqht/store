package com.qiuhongtao.search.controller;

import com.qiuhongtao.param.ProductParamsSearch;
import com.qiuhongtao.pojo.Product;
import com.qiuhongtao.search.service.SearchService;
import com.qiuhongtao.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("product")
    public R productList(@RequestBody ProductParamsSearch productParamsSearch) throws JsonProcessingException {


        return searchService.search(productParamsSearch);
    }

    @PostMapping("save")
    public R SaveProduct(@RequestBody Product product) throws IOException {

        return searchService.save(product);
    }

    @PostMapping("remove")
    public R RemoveProduct(@RequestBody Integer productId) throws IOException {

        return searchService.remove(productId);
    }
}
