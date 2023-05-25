package com.sr03.chat_salon.model;


public class ChatMessage {
    //    private MessageType type;
    private Integer id;
    private String sender;
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

    //default constructor
    public ChatMessage() {
        super();
    }

    public ChatMessage(Integer id,String sender, String content) {
//        this.type = type;
        this.id = id;
        this.content = content;
        this.sender = sender;
    }
    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
