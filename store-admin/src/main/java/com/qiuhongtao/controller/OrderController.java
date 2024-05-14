package com.qiuhongtao.controller;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("order")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    public Object list(PageParam pageParam){

        return orderService.list(pageParam);
    }

}
