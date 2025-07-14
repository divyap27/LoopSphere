package com.example.LoopShere.model;

import com.example.LoopShere.dto.PostRequest.Visibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 280)
    private String content;

    @ElementCollection
    private List<String> mediaUrls;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime scheduledAt;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}