package com.example.LoopShere.controller;
import com.example.LoopShere.model.Comments;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
@MessageMapping("/comment/{postId}")
    @SendTo("/topic/comments/{postId}")
    public Comments broadcastComment(@DestinationVariable Long postId, Comments comment) {
        return comment;
    }
}
