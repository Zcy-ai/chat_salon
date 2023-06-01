package com.sr03.chat_salon.config;

import com.sr03.chat_salon.controller.ChatServiceController;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration
public class SpringWebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatServiceController chatServiceController;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(chatServiceController, "/chat/{login}/{chatID}") // [2]
                .addInterceptors()
                .setAllowedOrigins("*");
    }
}
