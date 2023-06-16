package com.sr03.chat_salon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sr03.chat_salon.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        try {
            if (!isRedisConnected()) {
                // Redis 未连接，处理逻辑
                handleRedisNotConnected();
                return;
            }
            // 群聊key为群id保证群聊小心保有一份
            Long score = red.opsForZSet().zCard(chat) + 1;
            boolean res = red.opsForZSet().add(chat, jsonString, score);
            if (res) {
                log.info("[INFO] Message " + chatID + "-" + jsonString + " is added to Redis");
            } else {
                log.info("[WARN] Message " + chatID + "-" + jsonString + " failed to be added to Redis");
            }
        } catch (RedisConnectionFailureException e) {
            // Redis 连接失败异常处理
            handleRedisConnectionFailure();
        } catch (Exception e) {
            // 其他异常处理
            log.error("Failed to add chat history for chatID: " + chatID, e);
        }
    }

    private boolean isRedisConnected() {
        try {
            red.opsForValue().get("test-connection"); // 执行简单的 Redis 操作进行连接测试
            return true;
        } catch (RedisConnectionFailureException e) {
            return false;
        }
    }

    private void handleRedisNotConnected() {
        // 处理 Redis 未连接的逻辑
        System.out.println("Redis is not connected");
    }

    private void handleRedisConnectionFailure() {
        // 处理 Redis 连接失败的逻辑
        System.out.println("Failed to connect to Redis");
    }

    public Set<String> getChatHistoryByChatID(int chatID) {
        String chat = Integer.toString(chatID);
        try {
            Long score = red.opsForZSet().zCard(chat);
            long low = 0;
            // 获取消息集合，只显示最近50条消息
            if (score > 50) {
                low = score - 50;
            }
            return red.opsForZSet().range(chat, low, score);
        } catch (Exception e) {
            log.error("[ERROR] Failed to get chat history for chatID: " + chatID, e);
            return Collections.emptySet(); // 返回一个空的 Set 表示历史记录为空
        }
    }


    public void deleteChatHistory(int chatID) {
        String chat = Integer.toString(chatID);
        red.delete(chat);
    }
}
