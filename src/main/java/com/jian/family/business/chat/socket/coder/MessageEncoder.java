package com.jian.family.business.chat.socket.coder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jian.family.FamilyCloudApplication;
import com.jian.family.business.chat.dto.Message;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message<?>> {

    private ObjectMapper objectMapper;


    @Override
    public void init(EndpointConfig endpointConfig) {
        objectMapper = FamilyCloudApplication.applicationContext.getBean(ObjectMapper.class);
    }

    @Override
    public String encode(Message<?> object) throws EncodeException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new EncodeException(object, e.getMessage(), e);
        }
    }
}
