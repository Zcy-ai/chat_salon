package com.sr03.chat_salon.dao.impl;

import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.dao.UserDaoCustom;
import com.sr03.chat_salon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl implements UserDaoCustom {
    // @Lazy
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User CreateUser(String last_name, String first_name, String login, int admin, String gender, String pwd) {
        User user = new User(last_name, first_name, login, admin, gender, pwd);
        userDao.save(user);
        return user;
    }

    @Override
    @Transactional
    public void DeleteUserById(int id) {
        userDao.deleteById(id);
    }

}
