package com.pgs.chatservice.repository;

import com.pgs.chatservice.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> getAllByRoomId(String roomId);
}
