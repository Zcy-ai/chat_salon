package com.sr03.chat_salon.service.impl;

import com.sr03.chat_salon.dao.ChatRoomDao;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    private ChatRoomDao chatRoomDao;
    @Override
    @Transactional
    public ChatRoom addChatRoom(ChatRoom chatRoom) {
        chatRoomDao.addChatRoom(chatRoom);
        return chatRoom;
    }

    @Override
    @Transactional
    public boolean deleteChatRoomByID(int chatRoomID) {
        return chatRoomDao.deleteChatRoomByID(chatRoomID);
    }

    @Override
    @Transactional
    public boolean deleteChatRoomByName(String chatRoomName) {
        return chatRoomDao.deleteChatRoomByName(chatRoomName);
    }

    @Override
    public List<ChatRoom> findAllChatRoom() {
        return chatRoomDao.findAllChatRoom();
    }

    @Override
    public List<ChatRoom> findChatRoomByName(String chatRoomName) {
        return chatRoomDao.findChatRoomByName(chatRoomName);
    }

    @Override
    public ChatRoom findChatRoomByID(int chatRoomID) {
        return chatRoomDao.findChatRoomByID(chatRoomID);
    }

    @Override
    public List<ChatRoom> findChatRoomByUser(int userID) {
        return chatRoomDao.findChatRoomByUser(userID);
    }
}
