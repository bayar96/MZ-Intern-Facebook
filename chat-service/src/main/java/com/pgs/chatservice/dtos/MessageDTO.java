package com.pgs.chatservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {


    private String id;

    private String roomId;

    private String messageType;

    private String content;

    private String sender;

    private String messageTime;
}
