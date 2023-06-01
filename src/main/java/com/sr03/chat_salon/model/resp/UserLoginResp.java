package com.sr03.chat_salon.model.resp;

import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.User;

import java.util.List;

public class UserLoginResp {
    String firstName;
    String lastName;
    List<User> userList;
    List<ChatRoom> chatRoomList;

    public UserLoginResp() {
    }

    public UserLoginResp(String firstName,String lastName, List<User> userList, List<ChatRoom> chatRoomList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userList = userList;
        this.chatRoomList = chatRoomList;
    }

    public List<ChatRoom> getChatRoomList() {
        return chatRoomList;
    }

    public void setChatRoomList(List<ChatRoom> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }
}
