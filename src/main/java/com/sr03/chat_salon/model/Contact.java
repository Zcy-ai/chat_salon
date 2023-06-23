package com.sr03.chat_salon.model;

import javax.persistence.*;

@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Contact(User user, ChatRoom chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public Contact() {
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", user=" + user +
                ", chatRoom=" + chatRoom +
                '}';
    }
}
