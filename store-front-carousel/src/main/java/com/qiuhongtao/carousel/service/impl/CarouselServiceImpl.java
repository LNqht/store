package com.qiuhongtao.carousel.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiuhongtao.carousel.mapper.CarouselMapper;
import com.qiuhongtao.carousel.service.CarouselService;
import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.*;
import com.qiuhongtao.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 查询优先级最高的四条轮播图数据
     *   按照优先级查询数据库数据
     *   我们使用stream流,进行内存数据切割,保留4条数据!
     * @return
     */
    @Cacheable(value="list.carousel",key = "#root.methodName" ,cacheManager = "cacheManagerDay")
    @Override
    public R list() {

        //声明数量
        int limit = 4 ; //至多查询四条
        //查询数据库
        IPage<Carousel> iPage = new Page<>(1,limit);
        QueryWrapper<Carousel> carouselQueryWrapper = new QueryWrapper<>();
        carouselQueryWrapper.orderByDesc("priority");
        IPage<Carousel> page = carouselMapper.selectPage(iPage, carouselQueryWrapper);

        List<Carousel> carouselList = page.getRecords();
        long total = page.getTotal();
        System.out.println("total = " + total);

        return R.ok(carouselList);
    }


    @Override
    public R page(PageParam pageParam) {

        //分页参数
        IPage<Carousel> page = new Page<>(pageParam.getCurrentPage()
                ,pageParam.getPageSize());
        //查询参数获取
        page = carouselMapper.selectPage(page, null);

        List<Carousel> records = page.getRecords();
        long total = page.getTotal();

        R r = R.ok("查询轮播图数据成功!", records, total);

        log.info("CarouselServiceImpl.page业务结束，结果:{}",r);

        return r;
    }
    @Override
    public R save(Carousel carousel) {
        //商品数据保存
        int rows = carouselMapper.insert(carousel);

        if (rows == 0){
            return R.fail("轮播图保存失败!");
        }

        //保存成功,进行发送消息,product插入到es库中
        rabbitTemplate.convertAndSend("topic.ex","insert.carousel",carousel);
        return R.ok("轮播图保存成功!");
    }

    @Override
    public R update(Carousel carousel) {
        int rows = carouselMapper.updateById(carousel);

        if (rows > 0){
            return R.ok("轮播图修改成功!");
        }

        return R.fail("轮播图修改失败!");
    }

    @Override
    public R remove(Integer carouselId) {

        int rows = carouselMapper.deleteById(carouselId);

        if (rows == 0){

            return R.fail("删除轮播图失败!");
        }
        return R.ok("轮播图删除成功!");
    }
}
