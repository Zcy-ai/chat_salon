package com.sr03.chat_salon.model;


public class ChatMessage {
    //    private MessageType type;
    private String sender;
    private String firstName;
    private String lastName;
    private Integer chatRoom;
    private String content;
    private String timestamp;

    public ChatMessage(String sender, String firstName, String lastName, Integer chatRoom, String content, String timestamp) {
        this.sender = sender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatRoom = chatRoom;
        this.content = content;
        this.timestamp = timestamp;
    }
//    public enum MessageType {
//        CHAT,
//        IMAGE,
//        VOICE,
//    }

//    public MessageType getType() {
//        return type;
//    }

//    public void setType(MessageType type) {
//        this.type = type;
//    }
//    public Integer getId() {return id;}
//
//    public void setId(Integer id) {this.id = id;}
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(int chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setChatRoom(Integer chatRoom) {
        this.chatRoom = chatRoom;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    //default constructor
    public ChatMessage() {
        super();
    }


    @Override
    public String toString() {
        return "ChatMessage{" +
                "chatRoomID=" + chatRoom + '\'' +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
