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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(User user) {
//        String encryptedPassword = encrypt(user.getPassword());
//        user.setPassword(encryptedPassword);
//
//        return userDao.save(user);
        return null;
    }

    @Override
    public User login(String login, String password) {
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
    @Transactional(readOnly = true)
    public User findUserById(int id) {
        User user = userDao.findUserById(id);
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
    public void enableDisableById(int id) {
        userDao.enableDisableById(id);
    };
    @Override
    @Transactional
    public void addUser(User user) {
        // Chiffrement des mots de passe
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
    @Override
    public Page<User> searchUsers(String searchQuery, Pageable pageable, String sortBy) {
        return userDao.searchUsers(searchQuery, pageable, sortBy);
    }
    @Override
    public Page<User> getAllUsers(Pageable pageable, String sortBy) {
        return userDao.findAll(pageable, sortBy);
    }
    @Override
    public Page<User> getDisabledUsers(Pageable pageable, String sortBy) {
        return userDao.findDisabledUsers(pageable, sortBy);
    };
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
    public void modifyUser(User user) {
        userDao.modifyUser(user);
    }
}
