package com.qiuhongtao;

import com.qiuhongtao.clients.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;


@MapperScan("com.qiuhongtao.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {UserClient.class, CategoryClient.class, ProductClient.class, OrderClient.class, CarouselClient.class})  //添加客户端引用
@EnableCaching //开启缓存支持
public class AdminApplication  {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
