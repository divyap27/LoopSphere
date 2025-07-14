package com.example.LoopShere.repository;

import com.example.LoopShere.model.Group;
import com.example.LoopShere.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderEmailAndReceiverEmailOrReceiverEmailAndSenderEmailOrderByTimestampAsc(
            String sender1, String receiver1, String sender2, String receiver2);

    List<Message> findByGroupOrderByTimestampAsc(Group group);
}