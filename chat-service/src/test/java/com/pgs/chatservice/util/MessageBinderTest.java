package com.pgs.chatservice.util;

import com.pgs.chatservice.dtos.MessageDTO;
import com.pgs.chatservice.model.Message;
import com.pgs.chatservice.model.MessageType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageBinderTest {

    @InjectMocks
    private MessageBinder messageBinder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldBindMessageToMessageDTO() {


        MessageDTO chatMessageDTO = MessageDTO.builder()
                .content("message")
                .sender("someUser")
                .roomId("someRoom")
                .messageType(String.valueOf(MessageType.CHAT))
                .build();

        Message chatMessage = messageBinder.bind(chatMessageDTO);

        assertThat(chatMessage.getContent()).isEqualTo(chatMessageDTO.getContent());
        assertThat(chatMessage.getSender()).isEqualTo(chatMessageDTO.getSender());
        assertThat(chatMessage.getRoomId()).isEqualTo(chatMessageDTO.getRoomId());
        assertThat(chatMessage.getMessageType()).isEqualTo(MessageType.CHAT);
        assertThat(chatMessage.getMessageTime()).isCloseTo(Calendar.getInstance().getTime(), 1000);
    }
}