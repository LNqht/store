package com.qiuhongtao.service.impl;

import com.qiuhongtao.clients.CarouselClient;
import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.Carousel;
import com.qiuhongtao.utils.R;
import com.qiuhongtao.service.CarouselService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselClient carouselClient;



    @Override
    public Object listPage(PageParam pageParam) {
        R r = carouselClient.listPage(pageParam);
        log.info("CarouselServiceImpl.listPage业务结束，结果:{}",r);

        return r;
    }

    /**
     * 删除轮播图数据
     *    清空缓存
     *    调用轮播图服务
     * @param carouselId
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(value = "list.carousel",allEntries = true),
                    @CacheEvict(value = "carousel",key = "#carouselId")
            }
    )
    @Override
    public Object remove(Integer carouselId) {
        R r = carouselClient.remove(carouselId);
        log.info("CarouselServiceImpl.remove业务结束，结果:{}",r);

        return r;
    }

    /**
     * 修改轮播图信息
     * 1.修改轮播图信息
     * 2.清空轮播图缓存集合
     * 3.更新缓存es处理 [异步]
     *
     * @param carousel
     * @return
     */
    @CacheEvict(value = "list.carousel",allEntries = true)
    @CachePut(value = "carousel",key = "#carousel.carouselId")
    @Override
    public Object update(Carousel carousel) {
        R r = carouselClient.update(carousel);
        log.info("CarouselServiceImpl.update业务结束，结果:{}",r);

        return r;
    }

    /**
     * 保存轮播图业务!
     * 1.保存轮播图
     * 2.保存轮播图图片
     * 3.轮播图缓存数据处理 [注解]
     * 4.查询缓存es处理 [异步]
     *
     * @param carousel
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(value = "list.carousel",allEntries = true),
                    @CacheEvict(value = "carousel",allEntries = true)
            }
    )
    @Override
    public Object save(Carousel carousel) {

        R r = carouselClient.save(carousel);
        log.info("CarouselServiceImpl.save业务结束，结果:{}",r);

        return r;
    }
}
