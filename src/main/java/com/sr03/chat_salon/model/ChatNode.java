package com.sr03.chat_salon.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public class ChatNode {
    private String login; // 客户端login
    private String roomId; // room id
    private WebSocketSession session; // 客户端session
    private String addr; // 客户端连接地址
//    private String loginTime; // 登陆时间
    // DataQueue 消息队列（不确定，再看看）

    public ChatNode(String login, String roomId, WebSocketSession session, String addr) {
        this.login = login;
        this.roomId = roomId;
        this.session = session;
        this.addr = addr;
//        this.loginTime = loginTime;
    }

    public void sendMessage(ChatMessage message) throws IOException {
        System.out.println("准备发送消息到前端"+message);
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
    public String getRoomId() {return roomId;}
    public void setRoomId(String roomId) {this.roomId = roomId;}
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
