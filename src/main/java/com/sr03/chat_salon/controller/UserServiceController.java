package com.sr03.chat_salon.controller;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.model.resp.UserLoginResp;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.UserService;
import com.sr03.chat_salon.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);
    @RequestMapping("register")
    public String ShowRegisterForm() {
        return "register";
    }
    @PostMapping (value = "/register")
    @ResponseBody
    public ResponseEntity RegisterHandler(
            @RequestParam(value="lastName") String last_name,
            @RequestParam(value="firstName") String first_name,
            @RequestParam(value="login") String login,
            @RequestParam(value="admin") boolean admin,
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
            String message = "User " + login + " not found!";
            logger.info(message);
            UserLoginResp errorResponse = new UserLoginResp(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        if (!user.isEnabled()) {
            String message = "User " + login + " is not enabled!";
            logger.info(message);
            UserLoginResp errorResponse = new UserLoginResp(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        int maxLoginAttempts = 3; // Change this value as needed
        int loginAttempts = user.getLoginAttempts();
        if (loginAttempts >= maxLoginAttempts) {
            user.setLoginAttempts(0);
            user.setEnabled(false);
            userService.updateUser(user);
            String message = "User " + login + " has exceeded maximum login attempts. User is disabled.";
            logger.info(message);
            UserLoginResp errorResponse = new UserLoginResp(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        // 验证密码，验证成功跳转
        if (userService.authenticate(login, pwd)){
            // Reset login attempts on successful login
            user.setLoginAttempts(0);
            userService.updateUser(user);

            request.getSession().setAttribute("user", user.getLogin());
            String token = jwtTokenProvider.generateToken(login);
            logger.info("User "+login+" connected");
            // TODO 存储在线用户到redis
            List<User> user_list = null;
            if (user.getAdmin()) {
                user_list = userService.getAllUsers();
            }
            List<ChatRoom> chatRoomList = chatRoomService.findChatRoomByUser(user.getId());
            UserLoginResp resp = new UserLoginResp(user.getFirstName(),user.getLastName(), user_list, chatRoomList, token);
            return ResponseEntity.ok(resp);
        }else {
            // Increment login attempts on unsuccessful login
            user.setLoginAttempts(loginAttempts + 1);
            userService.updateUser(user);
            String message = "Invalid password for user " + login + ". Login attempts: " + (loginAttempts + 1);
            logger.info(message);
            UserLoginResp errorResponse = new UserLoginResp(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
//
//    @RequestMapping (value = "/getAllUsers")
//    public String getAllUsersHandler(Model model) {
//        List<User> user_list = userService.getAllUsers();
//        model.addAttribute("users", user_list);
//        return "admin";
//    }
//
//    @GetMapping (value = "/updateUser")
//    public String updateUserHandler() {
//        // TODO 编辑用户实现
//        return null;
//    }
//    @DeleteMapping (value = "/deleteUser/{user_id}")
//    @ResponseBody
//    public ResponseEntity deleteUserHandler(@PathVariable("user_id") int id){
//        userService.deleteUserById(id);
//        logger.info("User with id "+id+" deleted");
//        List<User> user_list = userService.getAllUsers();
//        UserLoginResp resp = new UserLoginResp("","", user_list, null,"");
//        return ResponseEntity.ok(resp);
//    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }

}