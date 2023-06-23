package com.sr03.chat_salon.controller;


import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.ChatHistoryService;
import com.sr03.chat_salon.service.UserService;
import com.sr03.chat_salon.utils.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class ChatHistoryController {
    @Autowired
    private ChatHistoryService chatHistoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/chat_history/{login}/{chatRoomID}/{token}")
    @ResponseBody
    public ResponseEntity<Set<String>> chatHistoryHandler(
        @PathVariable(value="login") String login,
        @PathVariable(value="chatRoomID") int chatRoomID,
        @PathVariable(value="token") String token) {
        // jwt authentication
        User user = userService.findUserByLogin(login);
        if (user == null) {
            logger.error("User "+login+" Not Found!");
            return ResponseEntity.notFound().build();
        } else if (!jwtTokenProvider.validateToken(token, user)) {
            logger.error("Token ERROR");
        }
        if (!chatHistoryService.isRedisConnected()) {
            System.out.println("Not connected to Redis");
            return ResponseEntity.notFound().build();
        }
        Set<String> chatHistory = chatHistoryService.getChatHistoryByChatID(chatRoomID);
        if (chatHistory != null) {
            logger.info("Get chatHistory of chatRoom "+chatRoomID);
            return ResponseEntity.ok(chatHistory);
        } else {
            logger.warn("Failed to get the chatHistory of chatRoom "+chatRoomID);
            return ResponseEntity.notFound().build();
        }
    }
}
