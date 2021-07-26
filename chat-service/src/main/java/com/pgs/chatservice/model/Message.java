package com.pgs.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("message")
public class Message {

  @Id
  private String id;

  private String roomId;

  private MessageType messageType;

  private String content;

  private String sender;

  private Date messageTime;
}
