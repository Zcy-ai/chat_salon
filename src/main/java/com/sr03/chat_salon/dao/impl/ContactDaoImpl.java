package com.sr03.chat_salon.dao.impl;

import com.sr03.chat_salon.dao.ContactDao;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.Contact;
import com.sr03.chat_salon.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactDaoImpl implements ContactDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    @Transactional()
    public Contact addContact(Contact contact) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(contact);
        return contact;
    }
    @Override
    @Transactional()
    public void deleteContact(int userID, int chatRoomID) {
        String hql = "delete from Contact c where c.user.id = :userID and c.chatRoom.id = :chatRoomID";
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("userID", userID);
        query.setParameter("chatRoomID", chatRoomID);
        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findAllContact() {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Contact> query = session.createQuery("from Contact");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findContactByUser(int userID) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Contact> query = session.createQuery("from Contact where user = :userID", Contact.class);
        query.setParameter("userID", userID);
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findContactByChatRoom(int chatRoomID) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Contact> query = session.createQuery("from Contact where chatRoom = :chatRoomID", Contact.class);
        query.setParameter("chatRoomID", chatRoomID);
        return query.list();
    }
    @Override
    @Transactional(readOnly = true)
    public Contact findContactByChatRoomLogin(int chatRoomID, String login){
        Session session = this.sessionFactory.getCurrentSession();
        Query<Contact> query = session.createQuery("from Contact where chatRoom.id = :chatRoomID and user.login = :login", Contact.class);
        query.setParameter("chatRoomID", chatRoomID);
        query.setParameter("login", login);
        List<Contact> contacts = query.getResultList();
        if (!contacts.isEmpty()) {
            return contacts.get(0);
        }
        return null;
    }
}
