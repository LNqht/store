package com.qiuhongtao.clients;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.Carousel;
import com.qiuhongtao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "carousel-service")
public interface CarouselClient {
    /**
     * 后台管理,展示轮播图接口
     * @param pageParam
     * @return
     */
    @PostMapping("/carousel/listpage")
    R listPage(@RequestBody PageParam pageParam);

    @PostMapping("/carousel/remove")
    R remove(@RequestBody Integer carouselId);

    @PostMapping("/carousel/update")
    R update(@RequestBody Carousel carousel);

    @PostMapping("/carousel/save")
    R save(@RequestBody Carousel carousel);
}
