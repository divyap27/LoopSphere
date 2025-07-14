package com.example.LoopShere.service;

import com.example.LoopShere.model.Post;
import com.example.LoopShere.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostScheduler {

    private static final Logger logger = LoggerFactory.getLogger(PostScheduler.class);

    private final PostRepository postRepository;

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void publishScheduledPosts() {
        logger.info("Running scheduled post publishing task at {}", LocalDateTime.now());
        try {
            List<Post> scheduledPosts = postRepository.findByStatusAndScheduledAtBefore("SCHEDULED", LocalDateTime.now());
            logger.info("Found {} scheduled posts to publish", scheduledPosts.size());
            for (Post post : scheduledPosts) {
                post.setStatus("PUBLISHED");
                postRepository.save(post);
                logger.info("Published post ID: {}", post.getId());
            }
        } catch (Exception e) {
            logger.error("Error publishing scheduled posts: {}", e.getMessage(), e);
        }
    }
}