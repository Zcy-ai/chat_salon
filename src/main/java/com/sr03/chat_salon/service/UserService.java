package com.sr03.chat_salon.service;

import com.sr03.chat_salon.model.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    User login(String login, String password);
    List<User> getAllUsers();
    User findUserByLogin(String login);
    List<User> findUserByChatRoom(int chatRoomID);
    void addUser(User user);
    void deleteUserByLogin(String login);
    void deleteUserById(int id);
    boolean authenticate(String login, String password);
}
