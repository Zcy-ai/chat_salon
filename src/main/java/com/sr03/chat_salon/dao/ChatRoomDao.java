package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.ChatRoom;

import java.util.List;

public interface ChatRoomDao {
    boolean addChatRoom(ChatRoom chat_room);
    boolean deleteChatRoomByID(int chatRoomID);
    boolean deleteChatRoomByName(String chatRoomName);
    List<ChatRoom> findAllChatRoom();
    List<ChatRoom> findChatRoomByName(String chatRoomName);
    ChatRoom findChatRoomByID(int chatRoomID);
    List<ChatRoom> findChatRoomByUser(int userID);
}
