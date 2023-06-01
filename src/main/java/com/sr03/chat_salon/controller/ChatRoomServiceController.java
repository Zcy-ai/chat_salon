package com.sr03.chat_salon.controller;

import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.Contact;
import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.model.resp.ChatRoomResp;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.ContactService;
import com.sr03.chat_salon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ChatRoomServiceController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomServiceController.class);
    @PostMapping(value = "/create_chatroom/{login}/{chatName}")
    @ResponseBody
    public ResponseEntity<ChatRoomResp> createChatRoomHandler(
            @PathVariable("login") String login,
            @PathVariable("chatName") String chatName) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            logger.info("User "+login+" Not Found!");
            return ResponseEntity.notFound().build();
        }
        // 持久化chatRoom
        ChatRoom chatRoom = new ChatRoom(chatName);
        chatRoomService.addChatRoom(chatRoom);
//        chatRoom.addUser(user); // 用这个函数会出现lazy proxy bug
        // 持久化创建者和chatRoom的contact
        Contact contact = new Contact(user, chatRoom);
        contactService.addContact(contact);
        ChatRoomResp resp = new ChatRoomResp(chatRoom.getId(),chatRoom.getName());
        return ResponseEntity.ok(resp);
    }

    @PostMapping(value = "/delete_chatroom/{user}/{chatRoomID}")
    @ResponseBody
    public ResponseEntity deleteChatRoomHandler(
            @PathVariable("user") String login,
            @PathVariable("chatRoomID") Integer chatRoomID) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            logger.info("User "+login+" Not Found!");
            return ResponseEntity.notFound().build();
        }
        // TODO 验证相应的Contact也被删除
        chatRoomService.deleteChatRoomByID(chatRoomID);
        return ResponseEntity.ok().build();
    }
}
