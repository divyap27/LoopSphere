package com.example.LoopShere.controller;

import com.example.LoopShere.dto.PostRequest;
import com.example.LoopShere.dto.PostResponse;
import com.example.LoopShere.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostCreationController {

    private static final Logger logger = LoggerFactory.getLogger(PostCreationController.class);

    @Autowired
    private PostService postService;

    // Create a new post with optional media
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestPart("post") PostRequest postRequest,
            @RequestPart(value = "media", required = false) List<MultipartFile> mediaFiles) {
        logger.info("Creating post for user ID: {}", postRequest.getUserId());
        PostResponse post = postService.createPost(postRequest, mediaFiles);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // Schedule a post for future publishing
    @PostMapping("/schedule")
    public ResponseEntity<PostResponse> schedulePost(
            @Valid @RequestPart("post") PostRequest postRequest,
            @RequestPart(value = "media", required = false) List<MultipartFile> mediaFiles) {
        logger.info("Scheduling post for user ID: {} at {}", postRequest.getUserId(), postRequest.getScheduledAt());
        PostResponse post = postService.schedulePost(postRequest, mediaFiles);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // Save a post as draft
    @PostMapping("/draft")
    public ResponseEntity<PostResponse> saveDraft(
            @Valid @RequestPart("post") PostRequest postRequest,
            @RequestPart(value = "media", required = false) List<MultipartFile> mediaFiles) {
        logger.info("Saving draft for user ID: {}", postRequest.getUserId());
        PostResponse post = postService.saveDraft(postRequest, mediaFiles);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Get AI-driven caption suggestions
    @GetMapping("/suggestions/caption")
    public ResponseEntity<List<String>> getCaptionSuggestions(@RequestParam String topic) {
        logger.info("Fetching caption suggestions for topic: {}", topic);
        List<String> suggestions = postService.getCaptionSuggestions(topic);
        return new ResponseEntity<>(suggestions, HttpStatus.OK);
    }

    // Get post by ID (for preview or editing)
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        logger.info("Fetching post with ID: {}", postId);
        PostResponse post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}