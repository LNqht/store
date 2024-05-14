package com.qiuhongtao.service;

import com.qiuhongtao.param.AdminUserParam;
import com.qiuhongtao.pojo.AdminUser;
import com.qiuhongtao.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;


public interface AdminUserService extends IService<AdminUser> {

    /**
     * 后台管理登录页面
     * @param adminUserParam
     * @return
     */
    R login(AdminUserParam adminUserParam);
}
