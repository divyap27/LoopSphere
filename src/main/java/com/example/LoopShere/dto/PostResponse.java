package com.example.LoopShere.dto;

import com.example.LoopShere.dto.PostRequest.Visibility;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostResponse {

    private Long id;
    private String content;
    private List<String> mediaUrls;
    private Visibility visibility;
    private LocalDateTime createdAt;
    private LocalDateTime scheduledAt;
    private String status;
    private Long userId;
}