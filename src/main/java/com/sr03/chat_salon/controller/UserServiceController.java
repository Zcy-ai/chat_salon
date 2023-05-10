package com.sr03.chat_salon.controller;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:SpringBootController
 * Package:com.bjpowernode.springboot.web
 * Description:<br/>
 */
@RestController
public class UserServiceController {
    @Autowired
    private UserService userService;
    @PostMapping (value = "/register")
    public @ResponseBody String RegisterHandler(
            @RequestParam(value="lastName") String last_name,
            @RequestParam(value="firstName") String first_name,
            @RequestParam(value="login") String login,
            @RequestParam(value="admin") int admin,
            @RequestParam(value="gender") String gender,
            @RequestParam(value="password") String password) {
        // 查找是否有重复的用户注册login findUserByLogin
        if (userService.findUserByLogin(login) != null) {
            //TODO 换成logback记录
            System.out.println("Login already registered :(");
            return null;
        }
        // TODO 给密码进行加密
        // 创建用户
        User user = new User(last_name, first_name, login, admin, gender, password);
        userService.addUser(user);
        // TODO 若为管理员，跳转到管理员页面
        if (user.getAdmin() == 1) {
            return null;
        } else if (user.getAdmin() == 0) { // TODO 若为普通用户，跳转到普通用户聊天页面
            return null;
        }
        return null;
    }

    @PostMapping (value = "/login")
    public @ResponseBody String LoginHandler(
            @RequestParam("login") String login,
            @RequestParam("password") String pwd) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            //TODO 换成logback记录
            System.out.println("User Login Not Found :(");
            return null;
        }
        // TODO 验证密码，验证成功跳转
        return login+pwd;
    }

    @GetMapping (value = "/getAllUsers")
    public @ResponseBody String getAllUsersHandler(Model model) {
        List<User> user_list = userService.getAllUsers();
        model.addAttribute("listOfCustomers", user_list);
        // TODO 返回admin主界面
        return "Admin";
    }

    @GetMapping (value = "/updateUser")
    public @ResponseBody String updateUserHandler() {
        return null;
    }

    @GetMapping (value = "/deleteUser/{id}")
    public @ResponseBody String deleteUserHandler(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "admin";
    }

}