package com.qiuhongtao.search.service.impl;

import com.qiuhongtao.param.ProductParamsSearch;
import com.qiuhongtao.pojo.Product;
import com.qiuhongtao.pojo.ProductDoc;
import com.qiuhongtao.search.service.SearchService;
import com.qiuhongtao.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 商品搜索
     * @param productParamsSearch
     * @return
     */
    @Override
    public R search(ProductParamsSearch productParamsSearch) throws JsonProcessingException {

        SearchRequest searchRequest = new SearchRequest("product");

        if (StringUtils.isEmpty(productParamsSearch.getSearch())){
            //如果为null,查询全部
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }else{
            //不为空 all字段进行搜索
            searchRequest.source().query(QueryBuilders.matchQuery("all",productParamsSearch.getSearch()));
        }

        //设置分页参数
        searchRequest.source().from(productParamsSearch.getFrom());
        searchRequest.source().size(productParamsSearch.getSize());

        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

        //结果集解析
        //获取集中的结果
        SearchHits hits = response.getHits();
        //获取符合的数量
        long total = hits.getTotalHits().value;

        SearchHit[] items = hits.getHits();

        List<Product> productList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (SearchHit item : items) {
            //获取单挑json数据
            String json = item.getSourceAsString();
            Product product = objectMapper.readValue(json, Product.class);
            productList.add(product);
        }

        return R.ok(null,productList,total);
    }

    @Override
    public R save(Product product) throws IOException {
        IndexRequest indexRequest = new IndexRequest("product")
                .id(product.getProductId().toString());

        ProductDoc productDoc = new ProductDoc(product);

        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writeValueAsString(productDoc);

        indexRequest.source(json, XContentType.JSON);

        client.index(indexRequest, RequestOptions.DEFAULT);

        return R.ok("数据同步成功!");
    }

    @Override
    public R remove(Integer productId) throws IOException {

        System.out.println("RabbitMQListener.remove");
        System.out.println("productId = " + productId);
        DeleteRequest request = new DeleteRequest("product")
                .id(productId.toString());

        client.delete(request,RequestOptions.DEFAULT);

        return R.ok("es库的数据删除成功!");
    }


}
