package com.qiuhongtao.controller;

import com.qiuhongtao.service.UserService;
import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.User;
import com.qiuhongtao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("listpage")
    public Object list(PageParam pageParam){

        return userService.listPage(pageParam);
    }


    @PostMapping("remove")
    public Object remove(Integer userId){

        if (userId == null){
            return R.fail("删除失败!");
        }
        return userService.remove(userId);
    }


    @PostMapping("update")
    public Object update(User user){

        return userService.update(user);
    }


    @PostMapping("save")
    public Object save(User user){

        return userService.save(user);
    }

}
