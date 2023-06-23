package com.sr03.chat_salon.model;

public class ChatMessage {
    private String sender;       // Expéditeur du message
    private String firstName;    // Prénom de l'expéditeur
    private String lastName;     // Nom de famille de l'expéditeur
    private Integer chatRoom;    // ID de la salle de chat
    private String content;      // Contenu du message
    private String timestamp;    // Horodatage du message

    public ChatMessage(String sender, String firstName, String lastName, Integer chatRoom, String content, String timestamp) {
        this.sender = sender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatRoom = chatRoom;
        this.content = content;
        this.timestamp = timestamp;
    }

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

    public Integer getChatRoom() {
        return chatRoom;
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

    // Constructeur par défaut
    public ChatMessage() {
        super();
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", chatRoom=" + chatRoom +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
