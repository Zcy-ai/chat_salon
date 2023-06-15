package com.sr03.chat_salon.controller;

import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminServiceController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public String adminLoginHandler(
        @RequestParam("login") String login,
        @RequestParam("password") String pwd) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            // TODO 换成logback记录
            System.out.println("User Login Not Found :(");
            return null;
        }
        // 验证密码，验证成功跳转
        if (userService.authenticate(login, pwd)) {
            if (user.getAdmin()){
                return "redirect:/admin/getAllUsers/all";
            }
            System.out.println("Permission denied :(");
        }
        return "index";
    }

    @RequestMapping(value = "/getAllUsers/{status}")
    public String getAllUsersHandler(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @PathVariable("status") String status) {

        // 定义分页参数
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);

        // 获取分页结果
        Page<User> pagedUsers;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            pagedUsers = userService.searchUsers(searchQuery, pageable, sortBy);
        } else {
            if ("disabled".equals(status)) {
                pagedUsers = userService.getDisabledUsers(pageable, sortBy);
            } else {
                pagedUsers = userService.getAllUsers(pageable, sortBy);
            }
        }

        // 添加分页结果和搜索关键词到模型
        model.addAttribute("pagedUsers", pagedUsers);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("status", status);
        return "admin";
    }

    @GetMapping(value = "/editUser/{id}")
    public String editUserHandler(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "editAdm";
    }

    @PostMapping(value = "/modifyUser")
    public String modifyUserHandler(@ModelAttribute User user) {
        userService.modifyUser(user);
        return "redirect:/admin/getAllUsers/all";
    }
    @PostMapping(value = "/deleteUser/{id}")
    public String deleteUserHandler(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/getAllUsers/all";
    }
    @PostMapping(value = "/enableDisableUser/{id}")
    public String enableDisableUserHandler(@PathVariable("id") int id) {
        userService.enableDisableById(id);
        return "redirect:/admin/getAllUsers/all";
    }
    @RequestMapping(value = "/createUser")
    public String createUsersHandler(){
        return "registerAdm";
    }
    @PostMapping (value = "/register")
    public String RegisterHandler(
            @RequestParam(value="lastName") String last_name,
            @RequestParam(value="firstName") String first_name,
            @RequestParam(value="login") String login,
            @RequestParam(value="admin") boolean admin,
            @RequestParam(value="gender") String gender,
            @RequestParam(value="password") String password,
            Model model) {
        // 查找是否有重复的用户注册login findUserByLogin
        if (userService.findUserByLogin(login) != null) {
            //TODO 换成logback记录
            model.addAttribute("error", "Login already registered!");
            System.out.println("Login already registered :(");
            return "registerAdm";
        }

        // 创建用户
        User user = new User(last_name, first_name, login, admin, gender, password);
        userService.addUser(user);
        return "redirect:/admin/getAllUsers/all";
    }
}
