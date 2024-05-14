package com.qiuhongtao.controller;

import com.qiuhongtao.clients.CategoryClient;
import com.qiuhongtao.pojo.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Slf4j
@Controller
@RequestMapping
public class HtmlJumpController {

    @Autowired
    private CategoryClient categoryClient;

    /**
     *  设计欢迎页面跳转controller
      * @return login 登录页面
     */
   @GetMapping({"/","index.html","index"})
   public String  welcome(){
       log.info("HtmlJumpController.welcome 跳转登录页面!");
       return "login";
   }


    /**
     * 登录成功跳转到index页面!
     * @return
     */
   @GetMapping("/home")
   public String home(){
       log.info("HtmlJumpController.home登录成功,跳转程序首页!index页面!");
       return "index";
   }

    /**
     * 跳转用户管理页面
     * @return
     */
    @GetMapping("/user")
    public String user(){
        log.info("HtmlJumpController.user,跳转用户管理!user页面!");
        return "user/user";
    }

    /**
     * 跳转商品管理页面
     * @return
     */
    @GetMapping("/product")
    public String product(){
        log.info("HtmlJumpController.product,跳转商品管理!product页面!");
        return "product/product";
    }


    /**
     * 跳转类别管理页面
     * @return
     */
    @GetMapping("/category")
    public String category(){
        log.info("HtmlJumpController.category,跳转类别管理!category页面!");
        return "category/category";
    }


    /**
     * 跳转订单管理页面
     * @return
     */
    @GetMapping("/order")
    public String order(){
        log.info("HtmlJumpController.order,跳转订单管理!order页面!");
        return "order/order";
    }

    /**
     * 跳转轮播图管理页面
     * @return
     */
    @GetMapping("/carousel")
    public String carousel() {
        log.info("HtmlJumpController.carousel,跳转轮播图管理!carousel页面!");
        return "carousel/carousel";
    }

    /**
     * 打开编辑轮播图页面
     * @return
     */
    @GetMapping("/carousel/update/html")
    public String carouselUpateHtml(){
        log.info("HtmlJumpController.carouselUpdateHtml业务结束，结果:{}");
        return "carousel/edit";
    }

    /**
     *
     */
    @GetMapping("/carousel/save/html")
    public String carouselSaveHtml(){
        log.info("HtmlJumpController.carouselSaveHtml业务结束，结果:{}");
        return "carousel/add";
    }
    /**
     * 打开编辑用户页面
     * @return
     */
    @GetMapping("/user/update/html")
    public String userUpdateHtml(){
        log.info("HtmlJumpController.userUpdateHtml业务结束，结果:{}");
        return "user/edit";
    }


    /**
     * 打开添加用户页面
     * @return
     */
    @GetMapping("/user/save/html")
    public String userSaveHtml(){
        log.info("HtmlJumpController.userSaveHtml业务结束，结果:{}");
        return "user/add";
    }




    /**
     * 打开编辑类别页面
     * @return
     */
    @GetMapping("/category/update/html")
    public String categoryUpdateHtml(){
        log.info("HtmlJumpController.categoryUpdateHtml业务结束，结果:{}");
        return "category/edit";
    }

    /**
     * 打开添加类别页面
     * @return
     */
    @GetMapping("/category/save/html")
    public String categorySaveHtml(){
        log.info("HtmlJumpController.categorySaveHtml结束，结果:{}");
        return "category/add";
    }


    /**
     * 打开添加商品页面
     * @return
     */
    @GetMapping("/product/save/html")
    public String productSaveHtml(Model model){
        log.info("HtmlJumpController.productSaveHtml业务结束，结果:{}");

        //查询类别列表,存入共享域
        List<Category> list = categoryClient.list();
        model.addAttribute("clist",list);
        return "product/add";
    }

    /**
     * 打开编辑商品页面
     * @return
     */
    @GetMapping("/product/update/html")
    public String productUpdateHtml(Model model){
        log.info("HtmlJumpController.productUpdateHtml业务结束，结果:{}");

        //查询类别列表,存入共享域
        List<Category> list = categoryClient.list();
        model.addAttribute("clist",list);
        return "product/edit";
    }
}
