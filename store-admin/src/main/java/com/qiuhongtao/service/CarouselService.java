package com.qiuhongtao.service;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.Carousel;
import org.springframework.web.bind.annotation.RequestBody;

public interface CarouselService {

    /**
     * 分页数据查询,轮播图模块
     * @param pageParam
     * @return
     */
    Object listPage(PageParam pageParam);

    /**
     * 删除轮播图数据
     * @param carouselId
     * @return
     */
    Object remove(Integer carouselId);

    /**
     * 修改轮播图数据
     * @param carousel
     * @return
     */
    Object update(Carousel carousel);

    /**
     * 保存轮播图数据,轮播图添加功能
     * @param carousel
     * @return
     */
    Object save(Carousel carousel);
}
