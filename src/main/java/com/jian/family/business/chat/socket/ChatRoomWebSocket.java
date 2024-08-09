package com.jian.family.business.chat.socket;

import com.jian.family.business.chat.dto.Message;
import com.jian.family.business.chat.dto.MessageType;
import com.jian.family.business.chat.socket.coder.MessageDecoder;
import com.jian.family.business.chat.socket.coder.MessageEncoder;
import com.jian.family.business.user.entity.UserEntity;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/transfer", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatRoomWebSocket {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private Session session;

    private Long id;

    private UserEntity getPrincipal() {
        var principal = (UsernamePasswordAuthenticationToken) session.getUserPrincipal();
        return (UserEntity) principal.getPrincipal();
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws Exception {
        log.debug("新连接：{}", session.getId());
        this.session = session;
        this.id = getPrincipal().getId();

        sessions.put(session.getId(), session);

        //连接成功时，给客户端发送会话ID
        session.getAsyncRemote().sendObject(createMessage(MessageType.ID, this.id), rst -> {
            if (!rst.isOK()) {
                log.error(rst.getException().getMessage(), rst.getException());
            }
        });
    }

    @OnMessage
    public void onMessage(Message<?> message) {
        log.debug("收到消息：{},{}", this.session.getId(), message);
        for (Session value : sessions.values()) {
            value.getAsyncRemote().sendObject(createMessage(message.type(), message.content()), rst -> {
                if (!rst.isOK()) {
                    log.error(rst.getException().getMessage(), rst.getException());
                }
            });
        }
    }

    @OnClose
    public void onClose(CloseReason reason) throws Exception {
        log.debug("链接断开:{}", this.session.getId());
        sessions.remove(session.getId()).close();
    }

    @OnError
    public void onError(Throwable throwable) throws IOException {
        log.debug("链接错误：{}", session.getId());
        log.debug(throwable.getMessage(), throwable);
    }

    private <T> Message<T> createMessage(MessageType type, T content) {
        return new Message<>(type, this.id, LocalDateTime.now(), content);
    }

}
