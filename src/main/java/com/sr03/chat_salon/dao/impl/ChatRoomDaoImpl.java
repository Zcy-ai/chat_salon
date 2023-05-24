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
    public void addChatRoom(ChatRoom chat_room) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(chat_room);
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
    public ChatRoom findChatRoomById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<ChatRoom> query = session.createQuery("from ChatRoom where id = :chatroom_id", ChatRoom.class);
        query.setParameter("chatroom_id", id);
        return query.uniqueResult();
    }
}
