package com.sr03.chat_salon.service.impl;

import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(User user) {
        // 对用户密码进行加密
//        String encryptedPassword = encrypt(user.getPassword());
//        user.setPassword(encryptedPassword);
//
//        return userDao.save(user);
        return null;
    }

    @Override
    public User login(String login, String password) {
//        // 对用户密码进行加密
//        String encryptedPassword = encrypt(password);
//
//        return userDao.findByLoginAndPassword(login, encryptedPassword);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAllUser();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByLogin(String login) {
        User user = userDao.findUserByLogin(login);
        return user;
    }

    @Override
    public List<User> findUserByChatRoom(int chatRoomID) {
        return userDao.findUserByChatRoom(chatRoomID);
    }

    @Override
    @Transactional
    public void deleteUserByLogin(String login) {
        userDao.deleteUserByLogin(login);
    }

    @Override
    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        // 对密码进行加密
        String security_pwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(security_pwd);
        userDao.addUser(user);
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = userDao.findUserByLogin(login);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

}
