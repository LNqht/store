package com.qiuhongtao.controller;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.Carousel;
import com.qiuhongtao.utils.R;
import com.qiuhongtao.config.AliyunOSSUtils;
import com.qiuhongtao.service.CarouselService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;


@RestController
@RequestMapping("carousel")
public class CarouselController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Autowired
    private CarouselService carouselService;

    @GetMapping("listpage")
    public Object list(PageParam pageParam){

        return carouselService.listPage(pageParam);
    }

    @PostMapping("upload")
    public Object upload(MultipartFile img) throws Exception {

        String filename = img.getOriginalFilename();
        String contentType = img.getContentType();
        long millis = System.currentTimeMillis();

        filename = millis + filename; //防止重复

        String url = aliyunOSSUtils.uploadImage(filename, img.getBytes(), contentType, 1000);
        System.out.println("url = " + url);
        return R.ok("上传成功",url);

    }

    @PostMapping("remove")
    public Object remove(Integer carouselId){

        if (carouselId == null){
            return R.fail("删除失败!");
        }
        return carouselService.remove(carouselId);
    }


    @PostMapping("update")
    public Object update(Carousel carousel){

        return carouselService.update(carousel);
    }


    @PostMapping("save")
    public Object save(Carousel carousel){

        return carouselService.save(carousel);
    }
}
