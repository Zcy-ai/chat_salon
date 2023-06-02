package com.sr03.chat_salon.model;

public class InviteMessage {
    private String inviter;
    private String receiver;
    private Integer chatRoomID;
    private String messageType;

    public InviteMessage() {
    }

    public InviteMessage(String inviter, String receiver, Integer chatRoomID, String messageType) {
        this.inviter = inviter;
        this.receiver = receiver;
        this.chatRoomID = chatRoomID;
        this.messageType = messageType;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(Integer chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
