package com.qiuhongtao.carousel.service;


import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.Carousel;
import com.qiuhongtao.utils.R;

public interface CarouselService {

    /**
     * 查询优先级最高的四条轮播图数据
     * @return
     */
    R list();

    R page(PageParam pageParam);

    R save(Carousel carousel);

    R update(Carousel carousel);

    R remove(Integer carouselId);
}
