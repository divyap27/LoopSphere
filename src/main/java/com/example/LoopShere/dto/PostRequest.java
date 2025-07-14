package com.example.LoopShere.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostRequest {

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 280, message = "Content cannot exceed 280 characters")
    private String content;

    private Visibility visibility; // PUBLIC, FRIENDS, PRIVATE
    private Long userId;
    private LocalDateTime scheduledAt;

    public enum Visibility {
        PUBLIC, FRIENDS, PRIVATE
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
}