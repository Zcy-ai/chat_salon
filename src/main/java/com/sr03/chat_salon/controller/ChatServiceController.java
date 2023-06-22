package com.sr03.chat_salon.controller;

import com.sr03.chat_salon.model.ChatMessage;
import com.sr03.chat_salon.model.ChatNode;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.ChatHistoryService;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.UserService;
import com.sr03.chat_salon.utils.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@Component
public class ChatServiceController extends TextWebSocketHandler {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ChatHistoryService chatHistoryService;
    private static Map<String, ChatNode> webSocketMap = new LinkedHashMap<>();
    private String login;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public ChatServiceController() {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String login = session.getUri().getPath().split("/")[2];
        String chatId = session.getUri().getPath().split("/")[3];
        String token = session.getUri().getPath().split("/")[4];
        this.login = login;
        // jwt Forensics
        User user = userService.findUserByLogin(login);
        if (user != null && jwtTokenProvider.validateToken(token, user)) {
            ChatNode chatNode = new ChatNode(login, chatId, session, session.getRemoteAddress().toString());
            log.info("Recevoir la session", session);
            webSocketMap.put(login, chatNode);
        } else {
          log.info("WARN: Can't establish the websocket connection!");
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Message reçu du client {} : {}", session.getId(), message.getPayload());
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage messageToSend = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        // Stocker les messages dans redis : key{chatRoomID} value{ChatMessage json format}
        chatHistoryService.addChatHistory(messageToSend.getChatRoom(), messageToSend);
        broadcast(messageToSend);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketMap.remove(this.login);
        log.info("Connexion fermée S:{}", session.getId());
    }

    public void broadcast(ChatMessage message) {
        ChatRoom chatRoom = chatRoomService.findChatRoomByID(message.getChatRoom());
        List<User> userList = userService.findUserByChatRoom(chatRoom.getId());
        for (User user : userList) {
            if (webSocketMap.containsKey(user.getLogin())) {
                try {
                    if (webSocketMap.get(user.getLogin()).getRoomId().equals(Integer.toString(message.getChatRoom()))){
                        webSocketMap.get(user.getLogin()).sendMessage(message);
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
