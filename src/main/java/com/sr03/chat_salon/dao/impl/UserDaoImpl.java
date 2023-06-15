package com.sr03.chat_salon.dao.impl;

import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.type.BooleanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public void enableDisableById(int id) {
        String hql = "update User u set u.enabled = case when u.enabled = true then false else true end where u.id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
    @Override
    @Transactional
    public void modifyUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println(user);
        String hql = "UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.gender = :gender, u.password = :pwd, u.admin = :admin, u.enabled = :enabled WHERE u.login = :login";
        session.createQuery(hql)
                .setParameter("firstName", user.getFirstName())
                .setParameter("lastName", user.getLastName())
                .setParameter("gender", user.getGender())
                .setParameter("pwd", user.getPassword())
                .setParameter("enabled", user.isEnabled(), BooleanType.INSTANCE)
                .setParameter("admin", user.getAdmin(), BooleanType.INSTANCE)
                .setParameter("login", user.getLogin())
                .executeUpdate();
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
    @Override
    @Transactional(readOnly = true)
    public User findUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User where id = :user_id", User.class);
        query.setParameter("user_id", id);
        return query.uniqueResult();
    }
    @Override
    @Transactional(readOnly = true)
    public Page<User> searchUsers(String searchQuery, Pageable pageable, String sortBy) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u WHERE u.firstName LIKE :searchQuery OR u.lastName LIKE :searchQuery ORDER BY u." + sortBy;
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("searchQuery", "%" + searchQuery + "%");
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<User> userList = query.getResultList();
        long total = getTotalUserCount(session, hql, searchQuery); // 获取总记录数
        return new PageImpl<>(userList, pageable, total);
    }

    private long getTotalUserCount(Session session, String hql, String searchQuery) {
        Query<Long> countQuery = session.createQuery("SELECT COUNT(*) " + hql, Long.class);
        countQuery.setParameter("searchQuery", "%" + searchQuery + "%");
        return countQuery.getSingleResult();
    }
    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable, String sortBy) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u ORDER BY u." + sortBy;
        Query<User> query = session.createQuery(hql, User.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<User> userList = query.getResultList();
        long total = getTotalUserCount(session, hql);
        return new PageImpl<>(userList, pageable, total);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<User> findDisabledUsers(Pageable pageable, String sortBy) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u where u.enabled = false ORDER BY u." + sortBy;
        Query<User> query = session.createQuery(hql, User.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<User> userList = query.getResultList();
        long total = getTotalUserCount(session, hql);
        return new PageImpl<>(userList, pageable, total);
    };
    private long getTotalUserCount(Session session, String hql) {
        Query<Long> countQuery = session.createQuery("SELECT COUNT(*) " + hql, Long.class);
        return countQuery.getSingleResult();
    }


}
