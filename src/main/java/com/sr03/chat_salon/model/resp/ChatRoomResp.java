package com.sr03.chat_salon.model.resp;

import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.User;
import java.util.Set;
import java.util.List;

public class ChatRoomResp {
    Integer id;
    String name;
    User chef;
    Set<User> users;
    public ChatRoomResp() {
    }
    public ChatRoomResp(Integer id, String name, User chef, Set<User> users) {
        this.id = id;
        this.name = name;
        this.chef = chef;
        this.users = users;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getChef() {
        return chef;
    }

    public void setChef(User chef) {
        this.chef = chef;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
