package com.sr03.chat_salon.model.resp;

import com.sr03.chat_salon.model.ChatRoom;

import java.util.List;

public class ChatRoomResp {
    Integer chatRoomId;
    String chatRoomName;
    public ChatRoomResp() {
    }

    public ChatRoomResp(Integer chatRoomId, String chatRoomName) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
    }

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }
}
