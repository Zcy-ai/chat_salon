package com.sr03.chat_salon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.Contact;
import com.sr03.chat_salon.model.InviteMessage;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.model.resp.ChatRoomResp;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.ContactService;
import com.sr03.chat_salon.service.UserService;
import com.sr03.chat_salon.utils.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Controller
public class ChatRoomServiceController {
    @Autowired
    private ChatRoomInvitationController chatRoomInvitationController;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomServiceController.class);
    @PostMapping(value = "/create_chatroom/{login}/{chatName}/{token}")
    @ResponseBody
    public ResponseEntity<ChatRoomResp> createChatRoomHandler(
            @PathVariable("login") String login,
            @PathVariable("chatName") String chatName,
            @PathVariable("token") String token) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            logger.info("[WARN]: User "+login+" Not Found!");
            return ResponseEntity.notFound().build();
        } else if (!jwtTokenProvider.validateToken(token, user)) {
            logger.info("[WARN]: Token ERROR");
        }
        // 持久化chatRoom
        ChatRoom chatRoom = new ChatRoom(chatName, user);
        chatRoomService.addChatRoom(chatRoom);
//        chatRoom.addUser(user); // 用这个函数会出现lazy proxy bug
        // 持久化创建者和chatRoom的contact
        Contact contact = new Contact(user, chatRoom);
        contactService.addContact(contact);
        ChatRoomResp resp = new ChatRoomResp(chatRoom.getId(),chatRoom.getName(),chatRoom.getChef(),chatRoom.getUsers());
        return ResponseEntity.ok(resp);
    }

    @PostMapping(value = "/delete_chatroom/{user}/{chatRoomID}/{token}")
    @ResponseBody
    @Transactional
    public ResponseEntity deleteChatRoomHandler(
            @PathVariable("user") String login,
            @PathVariable("chatRoomID") Integer chatRoomID,
            @PathVariable("token") String token) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            logger.info("[WARN]: User "+login+" Not Found!");
            return ResponseEntity.notFound().build();
        } else if (!jwtTokenProvider.validateToken(token, user)) {
            logger.info("[WARN]: Token ERROR");
        }
        ChatRoom chatroom = chatRoomService.findChatRoomByID(chatRoomID);
        if (chatroom.getChef().getId() == user.getId()){ // 如果是群主，则删除群
            Map<String, WebSocketSession> webSocketMap = chatRoomInvitationController.webSocketMap;
            Set<User> users = chatroom.getUsers();
//            System.out.println("用户");
//            System.out.println(users);
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                User currUser = iterator.next();
                String receiver = currUser.getLogin();
                if (receiver != login) { // 除了群主
                    InviteMessage message = new InviteMessage(login,receiver,chatRoomID,chatroom.getName(),"DELETEROOM");
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        String jsonString = objectMapper.writeValueAsString(message);
                        TextMessage textMessage = new TextMessage(jsonString);
                        webSocketMap.get(receiver).sendMessage(textMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            chatRoomService.deleteChatRoomByID(chatRoomID);
        }else{
            contactService.deleteContact(user.getId(), chatRoomID);//如果不是群主，删除他所对应的contact
        }
        return ResponseEntity.ok().build();
    }
}
