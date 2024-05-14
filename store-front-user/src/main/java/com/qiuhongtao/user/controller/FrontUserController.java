package com.qiuhongtao.user.controller;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.pojo.User;
import com.qiuhongtao.user.service.EmailService;
import com.qiuhongtao.user.service.UserService;
import com.qiuhongtao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("user")
public class FrontUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @GetMapping("list")
    public List<User> list(){

        return userService.list();
    }


    /**
     * 后台管理调用
     * @param pageParam
     * @return
     */
    @PostMapping("listpage")
    public R listPage(@RequestBody PageParam pageParam){

        return userService.listPage(pageParam);
    }

    /**
     * 后台管理调用,删除用户数据
     * @param userId
     * @return
     */
    @PostMapping("remove")
    public Object remove(@RequestBody Integer userId){

        return userService.remove(userId);
    }


    /**
     * 后台管理调用,修改用户数据
     * @param user
     * @return
     */
    @PostMapping("update")
    public  Object update(@RequestBody User user){

        return userService.update(user);
    }



    @PostMapping("check")
    public R check(@RequestBody User user){

        return userService.check(user.getUserName());
    }


    @PostMapping("register")
    public R register(@RequestBody User user){

        return userService.register(user);
    }


    @PostMapping("login")
    public R login(@RequestBody User user){

        return userService.login(user);
    }

    @PostMapping("getcode")
    public R email(@RequestBody Map<String,String> email, HttpSession httpSession){
        Random random = new Random();
        //生成随机验证码
        int code=1000+random.nextInt(8999);
        //把验证码存储到session中
        httpSession.setAttribute("code", code);
        //执行发送验证码
        if(emailService.sendEmail(email.get("email"), "用户注册验证码","欢迎注册,您的验证码为:"+code)) {
            return R.ok("获取成功");
        }
        return R.fail("获取失败");
    }

    @PostMapping("checkcode")
    public R code(@RequestBody Map<String,String> code, HttpSession httpSession){
        String emailCode = httpSession.getAttribute("code").toString();
        if(emailCode!=null) {
            if(emailCode.equals(code.get("code"))) {
                return R.ok("校验成功");
            }
        }
        return R.fail("校验失败");
    }
}
