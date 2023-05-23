package com.sr03.chat_salon.model;


public class ChatMessage {
//    private MessageType type;
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

    public ChatMessage(String sender, String content) {
//        this.type = type;
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
