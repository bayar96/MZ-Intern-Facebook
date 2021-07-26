package com.pgs.chatservice.util;


import com.pgs.chatservice.config.WebSocketEventListener;
import com.pgs.chatservice.model.Message;
import com.pgs.chatservice.model.MessageType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class WebSocketEventListenerTest {

    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @InjectMocks
    private WebSocketEventListener webSocketEventListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldHandleWebSocketDisconnectListener() {
        ArgumentCaptor<Message> chatMessageArgumentCaptor= ArgumentCaptor.forClass(Message.class);
        HashMap<String, Object> headers = new HashMap<>();
        HashMap<String, String> sessionInfo = new HashMap<>();
        sessionInfo.put("username", "someUser");
        headers.put("simpSessionAttributes", sessionInfo);

        SessionDisconnectEvent sessionDisconnectEvent = new SessionDisconnectEvent(new StompSubProtocolHandler(), new GenericMessage<>(new byte[0], headers), "12345", new CloseStatus(1000));

        webSocketEventListener.handleWebSocketDisconnectListener(sessionDisconnectEvent);

        verify(messagingTemplate, times(1)).convertAndSend(eq("/channel/"), chatMessageArgumentCaptor.capture());

        Message chatMessage = chatMessageArgumentCaptor.getValue();

        assertThat(chatMessage.getSender()).isEqualTo(sessionInfo.get("username"));
        assertThat(chatMessage.getMessageType()).isEqualTo(MessageType.LEAVE);
        assertThat(chatMessage.getContent()).isNull();
        assertThat(chatMessage.getMessageTime()).isNull();
        assertThat(chatMessage.getId()).isNull();

    }
}