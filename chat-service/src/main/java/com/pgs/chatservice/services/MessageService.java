package com.pgs.chatservice.services;

import com.pgs.chatservice.dtos.MessageDTO;
import com.pgs.chatservice.model.Message;

import java.util.List;

public interface MessageService {

    Message saveMessage(MessageDTO chatMessageDTO);

    List<Message> getAllMessages();


    List<Message> getAllByRoomId(String room);
}
