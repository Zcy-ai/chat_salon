package com.sr03.chat_salon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sr03.chat_salon.model.*;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.ContactService;
import com.sr03.chat_salon.service.UserService;
import com.sr03.chat_salon.utils.JwtTokenProvider;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

@CrossOrigin(origins = "*")
@Component
public class ChatRoomInvitationController extends TextWebSocketHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private  ChatRoomService chatRoomService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public static Map<String, WebSocketSession> webSocketMap = new LinkedHashMap<>();
    public ChatRoomInvitationController() {
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String login = session.getUri().getPath().split("/")[2];
        String token = session.getUri().getPath().split("/")[3];
        // jwt forensics
        User user = userService.findUserByLogin(login);
        if (user != null && jwtTokenProvider.validateToken(token, user)) {
            log.info("Recevoir la session", session);
            webSocketMap.put(login, session);
        }
    }
    @Override
    @Transactional
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        InviteMessage inviteMessage = objectMapper.readValue(message.getPayload(), InviteMessage.class);
        System.out.println(inviteMessage);
        if (inviteMessage.getMessageType().equals("CONFIRM")) {
            User user = userService.findUserByLogin(inviteMessage.getInviter());
            ChatRoom chatRoom = chatRoomService.findChatRoomByID(inviteMessage.getChatRoomID());
            Contact contact = new Contact(user, chatRoom);
            contactService.addContact(contact);
//            log.info("Add User"+user+" to the chatRoom "+chatRoom);
            sendMessage(inviteMessage);
            return;
        }
        Contact contact = contactService.findContactByChatRoomLogin(inviteMessage.getChatRoomID(),inviteMessage.getReceiver());
        if (contact != null) { // Les invités sont déjà dans le groupe
            String err = "Inviter is already in the chatRoom";
            log.info(err);
            inviteMessage.setReceiver(inviteMessage.getInviter()); //把错误传给自己
            inviteMessage.setMessageType(err);
        }else if(userService.findUserByLogin(inviteMessage.getReceiver()) == null) {
            String err = "Inviter doesn't exist";
            log.info(err);
            inviteMessage.setReceiver(inviteMessage.getInviter());
            inviteMessage.setMessageType(err);
        }
        sendMessage(inviteMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Connexion fermée S:{}", session.getId());
    }

    public void sendMessage(InviteMessage message) throws IOException {
        log.info("Invite message: "+message);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(message);
        TextMessage textMessage = new TextMessage(jsonString);
        webSocketMap.get(message.getReceiver()).sendMessage(textMessage);
    }
}
