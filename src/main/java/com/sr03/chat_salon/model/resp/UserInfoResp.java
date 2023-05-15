package com.sr03.chat_salon.model.resp;

import com.sr03.chat_salon.model.User;

import java.util.List;

public class UserInfoResp {
    int id;
    String name;
    List<User> attributes;

    public UserInfoResp(int id, String name, List<User> attributes) {
        this.id = id;
        this.name = name;
        this.attributes = attributes;
    }

    public UserInfoResp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<User> attributes) {
        this.attributes = attributes;
    }
}
