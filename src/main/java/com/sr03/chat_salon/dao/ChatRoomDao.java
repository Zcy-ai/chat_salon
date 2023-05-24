package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.ChatRoom;

import java.util.List;

public interface ChatRoomDao {
    void addChatRoom(ChatRoom chat_room);
    List<ChatRoom> findAllChatRoom();
    ChatRoom findChatRoomById(int id);
}
