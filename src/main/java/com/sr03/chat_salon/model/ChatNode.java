package com.sr03.chat_salon.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public class ChatNode {
    private String login;                 // Login du client
    private String roomId;                // ID de la salle de chat
    private WebSocketSession session;     // Session du client WebSocket
    private String addr;                  // Adresse de connexion du client

    public ChatNode(String login, String roomId, WebSocketSession session, String addr) {
        this.login = login;
        this.roomId = roomId;
        this.session = session;
        this.addr = addr;
    }

    public void sendMessage(ChatMessage message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(message);
        TextMessage textMessage = new TextMessage(jsonString);
        session.sendMessage(textMessage);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
