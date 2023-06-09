package com.sr03.chat_salon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sr03.chat_salon.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ChatHistoryService {
    private final StringRedisTemplate red;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public ChatHistoryService(StringRedisTemplate template) {
        this.red = template;
    }

    public void addChatHistory(int chatID, ChatMessage message) throws JsonProcessingException {
        String chat = Integer.toString(chatID);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(message);
        //群聊key为群id保证群聊小心保有一份
        Long score = red.opsForZSet().zCard(chat) + 1;
        boolean res = red.opsForZSet().add(chat,jsonString,score);
        if (res) {
            log.info("[INFO] Message " + chatID + "-" + jsonString + " is added to the redis");
        } else {
            log.info("[WARN] Message " + chatID + "-" + jsonString + " failed to be added to the redis");
        }
    }

    public Set<String> getChatHistoryByChatID(int chatID) {
        String chat = Integer.toString(chatID);
        Long score = red.opsForZSet().zCard(chat);
        long low = 0;
        //获取消息集合
        //只显示最近50条消息
        if (score > 50) {
            low = score-50;
        }
        return red.opsForZSet().range(chat, low, score);
    }

    public void deleteChatHistory(int chatID) {
        String chat = Integer.toString(chatID);
        red.delete(chat);
    }
}
