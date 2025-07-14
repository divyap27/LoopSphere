package com.example.LoopShere.repository;

import com.example.LoopShere.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.status = :status AND p.scheduledAt <= :currentTime")
    List<Post> findByStatusAndScheduledAtBefore(String status, LocalDateTime currentTime);
}