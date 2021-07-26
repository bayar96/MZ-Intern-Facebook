package com.pgs.chatservice.util;

import com.pgs.chatservice.dtos.MessageDTO;
import com.pgs.chatservice.model.Message;
import com.pgs.chatservice.model.MessageType;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class MessageBinder {

    public Message bind(MessageDTO messageDTO) {
        Date messageTime = Calendar.getInstance().getTime();
        setMessageTime(messageDTO, messageTime);

        return Message.builder()
                .id(null)
                .roomId(messageDTO.getRoomId())
                .content(messageDTO.getContent())
                .sender(messageDTO.getSender())
                .messageType(Enum.valueOf(MessageType.class, messageDTO.getMessageType()))
                .messageTime(messageTime)
                .build();
    }

    private void setMessageTime(MessageDTO messageDTO, Date messageTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String formattedMessageTime = dateFormat.format(messageTime);
        messageDTO.setMessageTime(formattedMessageTime);
    }
}