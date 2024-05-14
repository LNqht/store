package com.qiuhongtao.clients;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.param.ProductParamsString;
import com.qiuhongtao.pojo.Category;
import com.qiuhongtao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "category-service")
public interface CategoryClient {

    @GetMapping("/category")
    List<Category> list();

    @GetMapping("/category/{categoryName}")
    Category detail(@PathVariable String categoryName);


    @PostMapping("/category/names")
    List<Integer> names(@RequestBody ProductParamsString productParamsString);


    @PostMapping("/category/admin/list")
    R pageList(@RequestBody PageParam pageParam);


    @PostMapping("/category/admin/update")
    R update(@RequestBody  Category category);

    @PostMapping("/category/admin/remove")
    R remove(@RequestBody Integer categoryId);

    @PostMapping("/category/admin/save")
    R save(@RequestBody Category category);
}
