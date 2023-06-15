package com.sr03.chat_salon.service;

import com.sr03.chat_salon.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    User login(String login, String password);
    List<User> getAllUsers();
    Page<User> searchUsers(String searchQuery, Pageable pageable, String sortBy);
    Page<User> getAllUsers(Pageable pageable, String sortBy);
    Page<User> getDisabledUsers(Pageable pageable, String sortBy);
    User findUserByLogin(String login);
    User findUserById(int id);
    List<User> findUserByChatRoom(int chatRoomID);
    void addUser(User user);
    void deleteUserByLogin(String login);
    void deleteUserById(int id);
    void enableDisableById(int id);
    boolean authenticate(String login, String password);
    void updateUser(User user);
    void modifyUser(User user);
}
