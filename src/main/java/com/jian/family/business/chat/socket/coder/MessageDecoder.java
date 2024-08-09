package com.jian.family.business.chat.socket.coder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jian.family.FamilyCloudApplication;
import com.jian.family.business.chat.dto.Message;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

import java.io.IOException;

public class MessageDecoder implements Decoder.Text<Message<?>> {

    private ObjectMapper objectMapper;

    @Override
    public Message<?> decode(String s) throws DecodeException {
        try {
            return objectMapper.readValue(s, Message.class);
        } catch (IOException e) {
            throw new DecodeException(s, e.getMessage(), e);
        }
    }


    @Override
    public void init(EndpointConfig endpointConfig) {
        objectMapper = FamilyCloudApplication.applicationContext.getBean(ObjectMapper.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }
}
