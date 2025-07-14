package com.example.LoopShere.service;

import com.example.LoopShere.model.Message;
import com.example.LoopShere.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        if (message.getSenderEmail() == null || message.getSenderEmail().isEmpty()) {
            throw new IllegalArgumentException("senderEmail cannot be null or empty");
        }
        if (message.getContent() == null || message.getContent().isEmpty()) {
            throw new IllegalArgumentException("content cannot be null or empty");
        }
        if (message.getReceiverEmail() == null && message.getGroup() == null) {
            throw new IllegalArgumentException("Either receiverEmail or group must be specified");
        }
        System.out.println("Saving message: " + message);
        message.setTimestamp(LocalDateTime.now());
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save message: " + e.getMessage(), e);
        }
    }

    public List<Message> getChat(String user1, String user2) {
        return messageRepository.findBySenderEmailAndReceiverEmailOrReceiverEmailAndSenderEmailOrderByTimestampAsc(
                user1, user2, user2, user1
        );
    }
}