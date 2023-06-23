package com.sr03.chat_salon.controller;


import com.sr03.chat_salon.service.ChatHistoryService;
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
    private JwtTokenProvider jwtTokenProvider;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/chat_history/{chatRoomID}/{token}")
    @ResponseBody
    public ResponseEntity<Set<String>> chatHistoryHandler(
        @PathVariable(value="chatRoomID") int chatRoomID,
        @PathVariable(value="token") String token) {
        // TODO jwt认证
//        String login = jwtTokenProvider.getUserLoginFromJWT(token);
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
