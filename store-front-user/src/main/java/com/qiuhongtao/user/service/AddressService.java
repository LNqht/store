package com.qiuhongtao.user.service;

import com.qiuhongtao.param.AddressParam;
import com.qiuhongtao.utils.R;

public interface AddressService {

    /**
     * 查询地址列表
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 保存数据库数据
     * @param address
     * @return
     */
    R save(AddressParam address);

    /**
     * 删除地址数据
     * @param id
     * @return
     */
    R remove(Integer id);
}
