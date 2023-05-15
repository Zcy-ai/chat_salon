package com.sr03.chat_salon.model.resp;

import com.sr03.chat_salon.model.User;

import java.util.List;

public class UserLoginResp {
    String name;
    List<User> userList;

    public UserLoginResp() {
    }

    public UserLoginResp(String name, List<User> userList) {
        this.name = name;
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
