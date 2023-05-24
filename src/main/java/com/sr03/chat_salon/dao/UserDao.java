package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.User;

import java.util.List;

public interface UserDao {
    // 增
    void addUser(User user);
    // 删
    void deleteUserById(int id);
    void deleteUserByLogin(String login);
    // 改
    void updateUser(User user);
    // 查
    User findUserByLogin(String login);
    List<User> findAllUser();
}
