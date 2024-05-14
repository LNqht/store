package com.qiuhongtao.carousel.controller;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.Carousel;
import com.qiuhongtao.utils.R;
import com.qiuhongtao.carousel.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("carousel")
public class CarouselController  {

    @Autowired
    private CarouselService carouselService;


    @PostMapping("list")
    public R list(){
        return carouselService.list();
    }

    /**
     * 后台管理调用服务
     * @return
     */
    @PostMapping("listpage")
    public R page(@RequestBody PageParam pageParam){
        return carouselService.page(pageParam);
    }

    @PostMapping("save")
    public R save(@RequestBody Carousel carousel){
        return carouselService.save(carousel);
    }


    @PostMapping("update")
    public R update(@RequestBody Carousel carousel){
        return carouselService.update(carousel);
    }

    @PostMapping("remove")
    public R remove(@RequestBody Integer carouselId){

        return carouselService.remove(carouselId);
    }
}
