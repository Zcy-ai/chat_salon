package com.sr03.chat_salon.controller;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.model.resp.UserLoginResp;
import com.sr03.chat_salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public ResponseEntity RegisterHandler(
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
            return ResponseEntity.notFound().build();
        }
        // 创建用户
        User user = new User(last_name, first_name, login, admin, gender, password);
        userService.addUser(user);
        logger.info("User "+user.getLogin()+" created");
        return ResponseEntity.ok().build();
    }

    @PostMapping (value = "/login")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public ResponseEntity<UserLoginResp> LoginHandler(
            @RequestParam("login") String login,
            @RequestParam("password") String pwd,
            HttpServletRequest request) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            logger.info("User "+login+" Not Found!");
//            System.out.println("User Login Not Found :(");
            return ResponseEntity.notFound().build();
        }
        // 验证密码，验证成功跳转
        if (userService.authenticate(login, pwd)){
            request.getSession().setAttribute("user", user.getLogin());
            logger.info("User "+login+" connected");
            // TODO 存储在线用户到redis
            List<User> user_list = null;
            if (user.getAdmin() == 1) {
                user_list = userService.getAllUsers();
            }
            UserLoginResp resp = new UserLoginResp(user.getFirstName(),user.getLastName(), user_list);
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.notFound().build();
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
    @DeleteMapping (value = "/deleteUser/{user_id}")
    @ResponseBody
    public ResponseEntity deleteUserHandler(@PathVariable("user_id") int id){
        userService.deleteUserById(id);
        logger.info("User with id "+id+" deleted");
        List<User> user_list = userService.getAllUsers();
        UserLoginResp resp = new UserLoginResp("","", user_list);
        return ResponseEntity.ok(resp);
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }

}