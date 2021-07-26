package com.pgs.chatservice.controller;

import com.pgs.chatservice.dtos.MessageDTO;
import com.pgs.chatservice.model.Message;
import com.pgs.chatservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

  private final SimpMessageSendingOperations messagingTemplate;
  private final MessageService messageService;

  @MessageMapping("/chat/{roomId}/sendMessage")
  public void sendMessage(@DestinationVariable String roomId, @Payload MessageDTO message) {

        message.setRoomId(roomId);
        messageService.saveMessage(message);
        messagingTemplate.convertAndSend(format("/channel/%s", roomId), message);
  }

  @MessageMapping("/chat/{roomId}/addUser")
  public void addUser(@DestinationVariable String roomId, @Payload Message message,
                      SimpMessageHeaderAccessor headerAccessor) {

      headerAccessor.getSessionAttributes().put("username", message.getSender());
      message.setContent( message.getContent() + "- joined");
      messagingTemplate.convertAndSend(format("/channel/%s", roomId), message);
  }

    @SubscribeMapping("/messageHistory/{roomId}")
    public List<Message> getMessageHistory(@DestinationVariable String roomId) {
       return messageService.getAllByRoomId(roomId);
    }
}
