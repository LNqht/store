package com.qiuhongtao.product;

import com.qiuhongtao.clients.*;
import com.qiuhongtao.clients.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@MapperScan(basePackages = "com.qiuhongtao.product.mapper")
//开启feign客户端,引入对应的客户端
@EnableFeignClients(clients = {CategoryClient.class, SearchClient.class,
        OrderClient.class, CartClient.class, CollectClient.class})
@EnableCaching //开启缓存支持
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }

}
