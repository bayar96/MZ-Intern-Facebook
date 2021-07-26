package com.pgs.chatservice.services;

import com.pgs.chatservice.dtos.MessageDTO;
import com.pgs.chatservice.model.Message;
import com.pgs.chatservice.repository.MessageRepository;
import com.pgs.chatservice.util.MessageBinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService {
    private final MessageBinder messageBinder;
    private final MessageRepository messageRepository;

    @Override
    public Message saveMessage(MessageDTO chatMessageDTO) {

        Message chatMessage = messageBinder.bind(chatMessageDTO);
        log.info("Message saved.");
        return messageRepository.save(chatMessage);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> getAllByRoomId(String room) {
        return messageRepository.getAllByRoomId(room);
    }
}
