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
    private static final Logger logger = LoggerFactory.getLogger(ChatHistoryService.class);

    @GetMapping(value = "/chat_history/{chatRoomID}/{token}")
    @ResponseBody
    public ResponseEntity<Set<String>> chatHistoryHandler(
        @PathVariable(value="chatRoomID") int chatRoomID,
        @PathVariable(value="token") String token) {
        System.out.println(chatRoomID);
        // TODO jwt认证
//        String login = jwtTokenProvider.getUserLoginFromJWT(token);
        Set<String> chatHistory = chatHistoryService.getChatHistoryByChatID(chatRoomID);
        System.out.println(chatHistory);
        if (chatHistory != null) {
            return ResponseEntity.ok(chatHistory);
        } else {
            System.out.println("获取历史消息失败");
            return ResponseEntity.notFound().build();
        }
    }
}
