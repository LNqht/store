package com.qiuhongtao.service;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.Category;


public interface CategoryService {

    /**
     * 分页数据查询
     * @param pageParam
     * @return
     */
    Object listPage(PageParam pageParam);

    /**
     * 类别数据修改
     * @param category
     * @return
     */
    Object update(Category category);

    /**
     * 移除类别数据
     * @param categoryId
     * @return
     */
    Object remove(Integer categoryId);

    /**
     * 类别数据保存
     * @param category
     * @return
     */
    Object save(Category category);
}
