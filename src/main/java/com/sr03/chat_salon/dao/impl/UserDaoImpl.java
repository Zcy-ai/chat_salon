package com.sr03.chat_salon.dao.impl;

import com.sr03.chat_salon.dao.UserDaoCustom;
import com.sr03.chat_salon.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserDaoImpl implements UserDaoCustom {
    // @Lazy
//    @Autowired
//    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

//    private Session getCurrentSession() {
//        return sessionFactory.getCurrentSession();
//    }
//    @Override
//    @Transactional
//    public User CreateUser(String last_name, String first_name, String login, int admin, String gender, String pwd) {
//        User user = new User(last_name, first_name, login, admin, gender, pwd);
//        userDao.save(user);
//        return user;
//    }
//
//    @Override
//    @Transactional
//    public void DeleteUserById(int id) {
//        userDao.deleteById(id);
//    }
    @Override
    @Transactional
    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    @Transactional
    public List<User> findAllUser() {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User");
        return query.list();
    }
    @Override
    @Transactional
    public User findUserByLogin(String login) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User where login = :user_login", User.class);
        query.setParameter("user_login", login);
        return query.uniqueResult();
    }

}
