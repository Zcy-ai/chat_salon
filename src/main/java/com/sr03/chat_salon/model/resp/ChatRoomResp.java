package com.sr03.chat_salon.model.resp;

import com.sr03.chat_salon.model.ChatRoom;

import java.util.List;

public class ChatRoomResp {
    List<ChatRoom> chatRoomList;

    public ChatRoomResp() {
    }

    public ChatRoomResp(List<ChatRoom> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }

    public List<ChatRoom> getChatRoomList() {
        return chatRoomList;
    }

    public void setChatRoomList(List<ChatRoom> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }

    @Override
    public String toString() {
        return "ChatRoomResp{" +
                "chatRoomList=" + chatRoomList +
                '}';
    }
}
