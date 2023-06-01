package com.sr03.chat_salon.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ChatRoom")
public class ChatRoom {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToMany(targetEntity = User.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Contact",
            joinColumns = @JoinColumn(name = "chatRoom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();


    public ChatRoom() {
    }

    public ChatRoom(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        user.getChatRooms().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getChatRooms().remove(this);
    }
}

