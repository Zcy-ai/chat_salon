package com.sr03.chat_salon.controller;

import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
@RequestMapping("/admin")
public class AdminServiceController {
    @Autowired
    private UserService userService;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping(value = "/login")
    public String adminLoginHandler(
            @RequestParam("login") String login,
            @RequestParam("password") String pwd,
            Model model) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            System.out.println("User Login Not Found :(");
            model.addAttribute("errorMsg", "User Login Not Found :(");
            log.error("User Login Not Found :(");
            return "index";
        }
        if (userService.authenticate(login, pwd)) {
            if (user.getAdmin()) {
                return "redirect:/admin/getAllUsers/all";
            }
            log.warn("Permission denied :(");
            model.addAttribute("errorMsg", "Permission denied :(");
            return "index";
        }
        log.error("Password error :(");
        model.addAttribute("errorMsg", "Password error :(");
        return "index";
    }


    @RequestMapping(value = "/getAllUsers/{status}")
    public String getAllUsersHandler(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @PathVariable("status") String status) {

        // Définir les paramètres de pagination
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);

        // Obtenir les résultats de la pagination
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

        // Ajouter les résultats paginés et les termes de recherche au modèle
        model.addAttribute("pagedUsers", pagedUsers);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("status", status);
        return "admin";
    }

    @GetMapping(value = "/editUser/{id}")
    public String editUserHandler(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("prevPassword", user.getPassword());
        return "editAdm";
    }

    @PostMapping(value = "/modifyUser")
    public String modifyUserHandler(@ModelAttribute User user, @RequestParam String prevPassword) {
        if (!prevPassword.equals(user.getPassword())){ // Modifier le mot de passe s'il est différent deux fois
            String security_pwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(security_pwd);
        }
        userService.modifyUser(user);
        log.info("User has been modified");
        return "redirect:/admin/getAllUsers/all";
    }
    @PostMapping(value = "/deleteUser/{id}")
    public String deleteUserHandler(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        log.info("User "+id+" has been deleted");
        return "redirect:/admin/getAllUsers/all";
    }
    @PostMapping(value = "/enableDisableUser/{id}")
    public String enableDisableUserHandler(@PathVariable("id") int id) {
        userService.enableDisableById(id);
        log.info("Status of User "+id+" has been changed");
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
        // Déterminer s'il existe des utilisateurs en double enregistrés avec un login findUserByLogin
        if (userService.findUserByLogin(login) != null) {
            model.addAttribute("error", "Login already registered!");
            log.error("Login already registered :(");
            return "registerAdm";
        }

        // Créer un utilisateur
        User user = new User(last_name, first_name, login, admin, gender, password);
        userService.addUser(user);
        log.info(user+" has been created");
        return "redirect:/admin/getAllUsers/all";
    }
}
