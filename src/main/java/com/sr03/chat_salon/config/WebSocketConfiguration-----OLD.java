//package com.sr03.chat_salon.config;
//
////import com.sr03.chat_salon.controller.ChatServiceController;
//import com.sr03.chat_salon.service.ChatRoomService;
//import com.sr03.chat_salon.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfiguration {
////    注入一个serverEndpointExporter，该bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ChatRoomService chatRoomService;
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
//}