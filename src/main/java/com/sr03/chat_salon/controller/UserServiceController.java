package com.sr03.chat_salon.controller;
//import com.sr03.chat_salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:SpringBootController
 * Package:com.bjpowernode.springboot.web
 * Description:<br/>
 */
@RestController
public class UserServiceController {

    @PostMapping (value = "/register")
    public @ResponseBody String RegisterHandler(
            @RequestParam(value="lastName") String last_name,
            @RequestParam(value="firstName") String first_name,
            @RequestParam(value="login") String login,
            @RequestParam(value="admin") int admin,
            @RequestParam(value="gender") String gender,
            @RequestParam(value="password") String password) {
        // 查找是否有重复的用户注册login findUserByLoging
        // 给密码进行加密
        // 创建用户
        // 若为管理员，跳转到管理员页面
        // 若为普通用户，跳转到普通用户聊天页面
        System.out.println(last_name+first_name+login+admin+gender+password);
        return last_name+first_name+login+admin+gender+password;
    }

    @PostMapping (value = "/login")
    public @ResponseBody String LoginHandler(
            @RequestParam("login") String login,
            @RequestParam("password") String pwd) {
        System.out.println(login+pwd);
        return login+pwd;
    }

}