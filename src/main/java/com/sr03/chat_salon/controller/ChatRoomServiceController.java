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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ChatRoomServiceController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);
    @PostMapping(value = "chatRoom/create_chatroom/{user}/{chatRoomName}")
    @ResponseBody
    @Transactional
    public ResponseEntity createChatRoomHandler(
            @PathVariable("user") String login,
            @PathVariable("chatRoomName") String charRoomName) {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            logger.info("User "+login+" Not Found!");
            return ResponseEntity.notFound().build();
        }
        // 持久化chatRoom
        ChatRoom chatRoom = new ChatRoom(charRoomName);
        chatRoomService.addChatRoom(chatRoom);
//        chatRoom.addUser(user); // 用这个函数会出现lazy proxy bug
        // 持久化创建者和chatRoom的contact
        Contact contact = new Contact(user, chatRoom);
        contactService.addContact(contact);
        // 返回chatRoomList到前端
        List<ChatRoom> chatRoomList = chatRoomService.findChatRoomByUser(user.getId());
        ChatRoomResp resp = new ChatRoomResp(chatRoomList);
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
        List<ChatRoom> chatRoomList = chatRoomService.findChatRoomByUser(user.getId());
        ChatRoomResp resp = new ChatRoomResp(chatRoomList);
        return ResponseEntity.ok(resp);
    }
}
