package com.example.LoopShere.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recipientId; 
    private Long senderId;    
    private String type;      
    private String message;   
    private String link;      
    private boolean read;   
    private LocalDateTime createdAt;
}
