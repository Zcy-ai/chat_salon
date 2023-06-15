package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao {
    // 增
    void addUser(User user);
    // 删
    void deleteUserById(int id);
    void deleteUserByLogin(String login);
    // 改
    void updateUser(User user);
    void modifyUser(User user);
    // 查
    void enableDisableById(int id);
    User findUserByLogin(String login);
    User findUserById(int id);
    List<User> findAllUser();
    List<User> findUserByChatRoom(int chatRoomID);
    Page<User> searchUsers(String searchQuery, Pageable pageable, String sortBy);
    Page<User> findAll(Pageable pageable, String sortBy);
    Page<User> findDisabledUsers(Pageable pageable, String sortBy);
}
