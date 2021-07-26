package com.pgs.chatservice.config;

import com.pgs.chatservice.model.Message;
import com.pgs.chatservice.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

  private final SimpMessageSendingOperations messagingTemplate;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    log.info("Received a new web socket connection.");
  }


  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    Optional<String> username = Optional.ofNullable((String)
            Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username"));

    if (username.isPresent()){
      log.info("User Disconnected: " + username.toString());

      Message message = new Message();
      message.setMessageType(MessageType.LEAVE);
      message.setSender(username.get());
      messagingTemplate.convertAndSend("/channel/", message);
    }
  }
}
