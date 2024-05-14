package com.qiuhongtao.service;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.User;


public interface UserService {

    /**
     * 分页数据查询,用户模块
     * @param pageParam
     * @return
     */
    Object listPage(PageParam pageParam);

    /**
     * 删除用户数据
     * @param userId
     * @return
     */
    Object remove(Integer userId);

    /**
     * 修改用户数据
     * @param user
     * @return
     */
    Object update(User user);

    /**
     * 保存用户数据,用户注册功能
     * @param user
     * @return
     */
    Object save(User user);
}
