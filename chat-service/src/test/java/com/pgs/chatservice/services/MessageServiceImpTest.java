package com.pgs.chatservice.services;


import com.pgs.chatservice.dtos.MessageDTO;
import com.pgs.chatservice.model.Message;
import com.pgs.chatservice.model.MessageType;
import com.pgs.chatservice.repository.MessageRepository;
import com.pgs.chatservice.util.MessageBinder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


public class MessageServiceImpTest {

    @Mock
    private MessageBinder messageBinder;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceImp messageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveMessage() throws ParseException {
        Date messageTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedMessageTime = dateFormat.format(messageTime);

        MessageDTO chatMessageDTO = MessageDTO.builder()
                .sender("user")
                .roomId("room")
                .content("message")
                .messageTime(formattedMessageTime)
                .messageType(String.valueOf(MessageType.CHAT))
                .build();

        Message messageToSave = Message.builder()
                .sender(chatMessageDTO.getSender())
                .roomId(chatMessageDTO.getRoomId())
                .content(chatMessageDTO.getContent())
                .messageTime(messageTime)
                .messageType(MessageType.CHAT)
                .build();

        when(messageBinder.bind(chatMessageDTO)).thenReturn(messageToSave);
        when(messageRepository.save(messageToSave)).thenReturn(messageToSave);
        Message message =  messageService.saveMessage(chatMessageDTO);

        assertThat(message.getSender()).isEqualTo(chatMessageDTO.getSender());
        assertThat(message.getRoomId()).isEqualTo(chatMessageDTO.getRoomId());
        assertThat(message.getContent()).isEqualTo(chatMessageDTO.getContent());
        assertThat(message.getMessageType().toString()).isEqualTo(chatMessageDTO.getMessageType());

        Date fromDTO = dateFormat.parse(chatMessageDTO.getMessageTime());
        assertThat(message.getMessageTime()).isCloseTo(fromDTO, 1000);
    }
}