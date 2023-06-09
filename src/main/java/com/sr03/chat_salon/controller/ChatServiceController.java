package com.sr03.chat_salon.controller;

import com.sr03.chat_salon.model.ChatMessage;
import com.sr03.chat_salon.model.ChatNode;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.UserService;
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
    private static Map<String, ChatNode> webSocketMap = new LinkedHashMap<>();
    private String login;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public ChatServiceController() {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String login = session.getUri().getPath().split("/")[2];
        String chatId = session.getUri().getPath().split("/")[3];
        this.login = login;

        log.info("收到Session");
        String roomId = session.getUri().toString();
        System.out.println(roomId);
        String regex = "/(\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(roomId);
        if (matcher.find()) {
            String roomID = matcher.group(1);
            System.out.println(roomID);
            ChatNode chatNode = new ChatNode(login, roomID, session, session.getRemoteAddress().toString());
            webSocketMap.put(login, chatNode);
        } else {
            System.out.println("RoomID not found.");
        }
//        log.info("Utilisateur {} entre dans la salle du chat!", login);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("收到客户端{}消息：{}", session.getId(), message.getPayload());
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage messageToSend = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        broadcast(messageToSend);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketMap.remove(this.login);
        log.info("连接关闭S:{}", session.getId());
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
