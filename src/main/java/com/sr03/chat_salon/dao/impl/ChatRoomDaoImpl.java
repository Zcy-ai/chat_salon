package com.sr03.chat_salon.dao.impl;

import com.sr03.chat_salon.dao.ChatRoomDao;
import com.sr03.chat_salon.model.ChatRoom;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatRoomDaoImpl implements ChatRoomDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    @Transactional
    public boolean addChatRoom(ChatRoom chat_room) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(chat_room);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteChatRoomByID(int chatRoomID) {
        Session session = sessionFactory.getCurrentSession();
        ChatRoom chatRoom = session.get(ChatRoom.class, chatRoomID);
        if (chatRoom != null) {
            session.delete(chatRoom);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteChatRoomByName(String chatRoomName) {
        String hql = "delete from ChatRoom c where c.name = :chatRoomName";
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("chatRoomName", chatRoomName);
        query.executeUpdate();
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> findAllChatRoom() {
        Session session = this.sessionFactory.getCurrentSession();
        Query<ChatRoom> query = session.createQuery("from ChatRoom");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> findChatRoomByName(String chatRoomName) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<ChatRoom> query = session.createQuery("from ChatRoom where name = :chatRoomName");
        query.setParameter("chatRoomName", chatRoomName);
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public ChatRoom findChatRoomByID(int chatRoomID) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<ChatRoom> query = session.createQuery("from ChatRoom where id = :chatRoomID", ChatRoom.class);
        query.setParameter("chatRoomID", chatRoomID);
        return query.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> findChatRoomByUser(int userID) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "SELECT cr FROM ChatRoom cr JOIN cr.users u WHERE u.id = :userID";
        Query<ChatRoom> query = session.createQuery(hql);
        query.setParameter("userID", userID);
        return query.getResultList();
    }

}
