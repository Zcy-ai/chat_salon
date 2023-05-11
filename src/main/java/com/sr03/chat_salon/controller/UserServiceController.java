package com.sr03.chat_salon.controller;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserServiceController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);
    @RequestMapping("register")
    public String ShowRegisterForm() {
        return "register";
    }
    @PostMapping (value = "/register")
    public String RegisterHandler(
            @RequestParam(value="lastName") String last_name,
            @RequestParam(value="firstName") String first_name,
            @RequestParam(value="login") String login,
            @RequestParam(value="admin") int admin,
            @RequestParam(value="gender") String gender,
            @RequestParam(value="password") String password,
            Model model) {
        // 查找是否有重复的用户注册login findUserByLogin
        if (userService.findUserByLogin(login) != null) {
            // logback日志记录
            model.addAttribute("error", "Login already registered!");
            logger.error("Login already registered!");
            return "register";
        }
        // 创建用户
        User user = new User(last_name, first_name, login, admin, gender, password);
        userService.addUser(user);
        logger.info("User "+user.getLogin()+" created");
        return "/index";
    }

    @PostMapping (value = "/login")
    public String LoginHandler(
            @RequestParam("login") String login,
            @RequestParam("password") String pwd,
            HttpServletRequest request) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            logger.info("User "+login+" Not Found!");
//            System.out.println("User Login Not Found :(");
            return null;
        }
        // 验证密码，验证成功跳转
        if (userService.authenticate(login, pwd)){
            request.getSession().setAttribute("user", user.getLogin());
            logger.info("User "+login+" connected");
            return "redirect:/getAllUsers";
        }
        return "index";
    }

    @RequestMapping (value = "/getAllUsers")
    public String getAllUsersHandler(Model model) {
        List<User> user_list = userService.getAllUsers();
        model.addAttribute("users", user_list);
        return "admin";
    }

    @GetMapping (value = "/updateUser")
    public String updateUserHandler() {
        // TODO 编辑用户实现
        return null;
    }

    @PostMapping (value = "/deleteUser/{id}")
    public String deleteUserHandler(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        logger.info("User with id "+id+" deleted");
        return "redirect:getAllUsers";
    }

}