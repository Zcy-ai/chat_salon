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

    /**
     * Ajoute l'historique de chat dans Redis.
     *
     * @param chatID  ID de la salle de chat
     * @param message Message de chat
     * @throws JsonProcessingException Exception de traitement JSON
     */
    public void addChatHistory(int chatID, ChatMessage message) throws JsonProcessingException {
        String chat = Integer.toString(chatID);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(message);
        try {
            if (!isRedisConnected()) {
                // Redis non connecté, logique de traitement
                handleRedisNotConnected();
                return;
            }
            // La clé du chat de groupe est l'ID du groupe pour garantir qu'il n'y a qu'une seule copie du chat de groupe
            Long score = red.opsForZSet().zCard(chat) + 1;
            boolean res = red.opsForZSet().add(chat, jsonString, score);
            if (res) {
                log.info("Message " + chatID + "-" + jsonString + " is registered to Redis");
            } else {
                log.error("Message " + chatID + "-" + jsonString + " failed to be registered to Redis");
            }
        } catch (RedisConnectionFailureException e) {
            // Gestion de l'exception de défaillance de la connexion Redis
            handleRedisConnectionFailure();
        } catch (Exception e) {
            // Autre gestion d'exception
            log.error("Message " + chatID + "-" + jsonString + " failed to be registered to Redis");
        }
    }

    /**
     * Vérifie si Redis est connecté.
     *
     * @return true si Redis est connecté, sinon false
     */
    public boolean isRedisConnected() {
        try {
            red.opsForValue().get("test-connection"); // Exécute une opération Redis simple pour tester la connexion
            return true;
        } catch (RedisConnectionFailureException e) {
            return false;
        }
    }

    /**
     * Gère la logique lorsque Redis n'est pas connecté.
     */
    private void handleRedisNotConnected() {
        // Gérer la logique lorsque Redis n'est pas connecté
        log.warn("Redis is not connected");
    }

    /**
     * Gère la logique en cas d'échec de la connexion à Redis.
     */
    private void handleRedisConnectionFailure() {
        // Gérer la logique en cas d'échec de la connexion à Redis
        log.warn("Fail to connect to Redis");
    }

    /**
     * Obtient l'historique de chat par ID de chat.
     *
     * @param chatID ID de la salle de chat
     * @return Ensemble de l'historique de chat
     */
    public Set<String> getChatHistoryByChatID(int chatID) {
        String chat = Integer.toString(chatID);
        try {
            Long score = red.opsForZSet().zCard(chat);
            long low = 0;
            // Obtenir l'ensemble de messages, afficher uniquement les 50 derniers messages
            if (score > 50) {
                low = score - 50;
            }
            return red.opsForZSet().range(chat, low, score);
        } catch (Exception e) {
            log.error("Fail to get chat history of chatID : " + chatID, e);
            return Collections.emptySet(); // Retourne un ensemble vide pour indiquer que l'historique est vide
        }
    }

    /**
     * Supprime l'historique de chat d'une salle de chat.
     *
     * @param chatID ID de la salle de chat
     */
    public void deleteChatHistory(int chatID) {
        String chat = Integer.toString(chatID);
        red.delete(chat);
    }
}
