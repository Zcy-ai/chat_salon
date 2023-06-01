package com.sr03.chat_salon.model;


public class ChatMessage {
    //    private MessageType type;
    private Integer id;
    private String sender;
    private Integer chatRoom;
    private String content;
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
    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}
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

    //default constructor
    public ChatMessage() {
        super();
    }

    public ChatMessage(Integer id,String sender, Integer chatRoom, String content) {
//        this.type = type;
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.chatRoom = chatRoom;
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
