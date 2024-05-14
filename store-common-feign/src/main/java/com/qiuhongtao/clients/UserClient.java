package com.qiuhongtao.clients;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.User;
import com.qiuhongtao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "user-service")
public interface UserClient {

    /**
     * 后台管理,展示用户信息接口
     * @param pageParam
     * @return
     */
    @PostMapping("/user/listpage")
    R listPage(@RequestBody PageParam pageParam);

    @PostMapping("/user/remove")
    R remove(@RequestBody Integer userId);

    @PostMapping("/user/update")
    R update(@RequestBody User user);

    @PostMapping("/user/register")
    R save(@RequestBody User user);
}
