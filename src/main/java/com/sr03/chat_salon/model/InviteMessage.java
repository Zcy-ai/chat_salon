package com.sr03.chat_salon.model;

public class InviteMessage {
    private String inviter; // L'expéditeur de l'invitation
    private String receiver; // Le destinataire de l'invitation
    private Integer chatRoomID; // L'identifiant de la salle de chat
    private String chatRoomName; // Le nom de la salle de chat
    private String messageType; // Le type de message d'invitation

    public InviteMessage() {
    }

    public InviteMessage(String inviter, String receiver, Integer chatRoomID, String chatRoomName, String messageType) {
        this.inviter = inviter;
        this.receiver = receiver;
        this.chatRoomID = chatRoomID;
        this.chatRoomName = chatRoomName;
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

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    @Override
    public String toString() {
        return "InviteMessage{" +
                "inviter='" + inviter + '\'' +
                ", receiver='" + receiver + '\'' +
                ", chatRoomID=" + chatRoomID +
                ", chatRoomName='" + chatRoomName + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
