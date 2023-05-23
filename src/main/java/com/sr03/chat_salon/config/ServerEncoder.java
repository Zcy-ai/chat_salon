package com.sr03.chat_salon.config;

import com.alibaba.fastjson.JSON;
import com.sr03.chat_salon.model.ChatMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class ServerEncoder implements Encoder.Text<ChatMessage> {

    @Override
    public void destroy() {
    }

    @Override
    public void init(EndpointConfig arg0) {
    }

    @Override
    public String encode(ChatMessage message) throws EncodeException {
        try {
            return JSON.toJSON(message).toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

}

