package com.sr03.chat_salon.model;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public class ChatNode {
    private String login; // 客户端login
    private Session session; // 客户端session
    private String addr; // 客户端连接地址
//    private String loginTime; // 登陆时间
    // DataQueue 消息队列（不确定，再看看）

    public ChatNode(String login, Session session, String addr) {
        this.login = login;
        this.session = session;
        this.addr = addr;
//        this.loginTime = loginTime;
    }

    public void sendMessage(ChatMessage message) throws IOException, EncodeException {
        // TODO 给session发送message
        System.out.println("准备发送消息到前端"+message);
        session.getBasicRemote().sendObject(message);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
