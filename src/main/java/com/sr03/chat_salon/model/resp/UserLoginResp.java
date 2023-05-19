package com.sr03.chat_salon.model.resp;

import com.sr03.chat_salon.model.User;

import java.util.List;

public class UserLoginResp {
    String firstName;
    String lastName;
    List<User> userList;

    public UserLoginResp() {
    }

    public UserLoginResp(String firstName,String lastName, List<User> userList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userList = userList;
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
