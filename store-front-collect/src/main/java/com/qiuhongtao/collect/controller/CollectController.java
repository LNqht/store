package com.qiuhongtao.collect.controller;

import com.qiuhongtao.collect.service.CollectService;
import com.qiuhongtao.param.CollectParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("collect")
public class CollectController {

    @Autowired
    private CollectService collectService;


    @PostMapping("save")
    public Object save(@RequestBody CollectParam collectParam){

        return collectService.save(collectParam);
    }


    @PostMapping("list")
    public Object list(@RequestBody CollectParam collectParam){

        return collectService.list(collectParam);
    }

    @PostMapping("remove")
    public Object remove(@RequestBody CollectParam collectParam){

        return collectService.remove(collectParam);
    }


    /**
     * 根据商品id删除
     * @param productId
     * @return
     */
    @PostMapping("remove/bypid")
    public Object removeByPid(@RequestBody Integer productId){

        return collectService.removeByPid(productId);
    }


}
