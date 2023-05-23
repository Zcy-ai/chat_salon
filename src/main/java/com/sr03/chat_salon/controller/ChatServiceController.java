package com.sr03.chat_salon.controller;

import com.sr03.chat_salon.config.ServerEncoder;
import com.sr03.chat_salon.model.ChatMessage;
import com.sr03.chat_salon.model.ChatNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@ServerEndpoint(value = "/chat/{login}", encoders = {ServerEncoder.class}) //接受websocket请求路径
@CrossOrigin(origins = "*")
@Component  //注册到spring容器中
public class ChatServiceController {
    //保存所有在线socket连接
    private static Map<String, ChatNode> webSocketMap = new LinkedHashMap<>();
    //记录当前在线数目
    private String login;
    //当前连接（每个websocket连入都会创建一个MyWebSocket实例
    private Session session;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public ChatServiceController() {
    }

    //处理连接建立
    @OnOpen
    public void onOpen(Session session, @PathParam("login") String login){
        // 创建一个ChatNode实例并保存到websocketMap中
        this.session = session;
        this.login = login;
        ChatNode chat_node = new ChatNode(login, session, session.getBasicRemote().toString());
        webSocketMap.put(login, chat_node);
        // TODO 为什么一直显示进入chat
        // System.out.println(webSocketMap);
        // log.info("Utilisateur {} entre dans la salle du chat!", login);
    }

    //接受消息
    @OnMessage
    public void onMessage(String message,Session session) throws EncodeException, IOException {
        log.info("收到客户端{}消息：{}",session.getId(),message);
        System.out.println(message);
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage message_to_send = objectMapper.readValue(message, ChatMessage.class);
//        session.getBasicRemote().sendObject(message_to_send);
        broadcast(message_to_send);
    }

    //处理错误
    @OnError
    public void onError(Throwable error,Session session){
        log.info("发生错误{},{}",session.getId(),error.getMessage());
    }

    //处理连接关闭
    @OnClose
    public void onClose(){
        webSocketMap.remove(this.session.getId());
        log.info("连接关闭:{}",this.session.getId());
    }


    //广播消息
    public void broadcast(ChatMessage message){
        ChatServiceController.webSocketMap.forEach((k,v)->{
            try{
//                v.sendMessage("这是一条测试广播");
                v.sendMessage(message);
            }catch (Exception e){
            }
        });
    }

}


