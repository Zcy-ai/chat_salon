package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.User;

public interface UserDaoCustom {
    // User FindUserById(int id);
    User CreateUser(String last_name, String first_name, String login, int admin, String gender, String pwd);
    void DeleteUserById(int id);
}
