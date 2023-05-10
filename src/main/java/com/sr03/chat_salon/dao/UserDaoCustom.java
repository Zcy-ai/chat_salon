package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.User;

import java.util.List;

public interface UserDaoCustom {
    // User FindUserById(int id);
//    User CreateUser(String last_name, String first_name, String login, int admin, String gender, String pwd);
    User findUserByLogin(String login);
    List<User> findAllUser();
    void addUser(User user);
}
