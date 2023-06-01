package com.sr03.chat_salon.dao.impl;

import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Session;
import org.hibernate.query.Query;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        session.delete(user);
    }

    @Override
    @Transactional
    public void deleteUserByLogin(String login) {
        String hql = "delete from User u where u.login = :login";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("login", login);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }
    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUser() {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUserByChatRoom(int chatRoomID) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "SELECT DISTINCT u FROM User u JOIN FETCH u.chatRooms JOIN u.chatRooms cr WHERE cr.id = :chatRoomID";
        Query<User> query = session.createQuery(hql);
        query.setParameter("chatRoomID", chatRoomID);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByLogin(String login) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User where login = :user_login", User.class);
        query.setParameter("user_login", login);
        return query.uniqueResult();
    }

}
