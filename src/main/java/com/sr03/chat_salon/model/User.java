package com.sr03.chat_salon.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="User")
public class User {
    @Column(name="lastName", nullable = false, length = 50)
    private String lastName;
    @Column(name="firstName", nullable = false, length = 50)
    private String firstName;
    @Column(name="login", nullable = false, length = 50, unique = true)
    private String login;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="gender", nullable = false, length = 6)
    private String gender;
    @Column(name="admin", nullable = false)
    private int admin;
    @Id
    @GeneratedValue
    private int id;

    @ManyToMany(targetEntity = ChatRoom.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Contact",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chatRoom_id")
    )
    private Set<ChatRoom> chatRooms = new HashSet<>();

    public User() {
    }

    public User(String lastName, String firstName, String login, int admin, String gender, String pwd) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.gender = gender;
        this.password = pwd;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setPwd(String pwd) {
        this.password = pwd;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Set<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void addChatRoom(ChatRoom chatRoom) {
        chatRooms.add(chatRoom);
        chatRoom.getUsers().add(this);
    }

    public void removeChatRoom(ChatRoom chatRoom) {
        chatRooms.remove(chatRoom);
        chatRoom.getUsers().remove(this);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.lastName);
        hash = 97 * hash + Objects.hashCode(this.firstName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        return true;
    }



    public User(String lastName, String firstName){
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "User{" + "lastName=" + lastName + ", firstName=" + firstName + ""
                + ", login=" + login  + ", gender=" + gender + ","
                + " pwd=" + password + '}';
    }

}


