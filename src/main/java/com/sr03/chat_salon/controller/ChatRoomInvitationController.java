package com.sr03.chat_salon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sr03.chat_salon.model.*;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.ContactService;
import com.sr03.chat_salon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@Component
public class ChatRoomInvitationController extends TextWebSocketHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private  ChatRoomService chatRoomService;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static Map<String, WebSocketSession> webSocketMap = new LinkedHashMap<>();
    public ChatRoomInvitationController() {
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String login = session.getUri().getPath().split("/")[2];
        webSocketMap.put(login,session);
    }
    @Override
    @Transactional // TODO 待定
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        InviteMessage inviteMessage = objectMapper.readValue(message.getPayload(), InviteMessage.class);
        if (inviteMessage.getMessageType().equals("CONFIRM")) {
            User user = userService.findUserByLogin(inviteMessage.getReceiver());
            ChatRoom chatRoom = chatRoomService.findChatRoomByID(inviteMessage.getChatRoomID());
            Contact contact = new Contact(user, chatRoom);
            contactService.addContact(contact);
            log.info("Add User"+user+" to the chatRoom "+chatRoom);
        }
        sendMessage(inviteMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("连接关闭S:{}", session.getId());
    }

    public void sendMessage(InviteMessage message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(message);
        TextMessage textMessage = new TextMessage(jsonString);
        webSocketMap.get(message.getReceiver()).sendMessage(textMessage);
    }
}
