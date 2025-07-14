package com.example.LoopShere.service;

import com.example.LoopShere.dto.PostRequest;
import com.example.LoopShere.dto.PostResponse;
import com.example.LoopShere.model.Post;
import com.example.LoopShere.model.User;
import com.example.LoopShere.repository.PostRepository;
import com.example.LoopShere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public PostResponse createPost(PostRequest postRequest, List<MultipartFile> mediaFiles) {
        logger.info("Creating post for user ID: {}", postRequest.getUserId());
        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setVisibility(postRequest.getVisibility());
        post.setCreatedAt(LocalDateTime.now());
        post.setStatus("PUBLISHED");
        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);

        // Handle media uploads
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            List<String> mediaUrls = new ArrayList<>();
            for (MultipartFile file : mediaFiles) {
                String mediaUrl = fileStorageService.storeFile(file);
                mediaUrls.add(mediaUrl);
            }
            post.setMediaUrls(mediaUrls);
        }

        Post savedPost = postRepository.save(post);
        return mapToPostResponse(savedPost);
    }

    public PostResponse schedulePost(PostRequest postRequest, List<MultipartFile> mediaFiles) {
        logger.info("Scheduling post for user ID: {} at {}", postRequest.getUserId(), postRequest.getScheduledAt());
        if (postRequest.getScheduledAt() == null || postRequest.getScheduledAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Scheduled time must be in the future");
        }
        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setVisibility(postRequest.getVisibility());
        post.setCreatedAt(LocalDateTime.now());
        post.setScheduledAt(postRequest.getScheduledAt());
        post.setStatus("SCHEDULED");
        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);

        // Handle media uploads
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            List<String> mediaUrls = new ArrayList<>();
            for (MultipartFile file : mediaFiles) {
                String mediaUrl = fileStorageService.storeFile(file);
                mediaUrls.addAll(mediaUrls);
            }
            post.setMediaUrls(mediaUrls);
        }

        Post savedPost = postRepository.save(post);
        return mapToPostResponse(savedPost);
    }

    public PostResponse saveDraft(PostRequest postRequest, List<MultipartFile> mediaFiles) {
        logger.info("Saving draft for user ID: {}", postRequest.getUserId());
        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setVisibility(postRequest.getVisibility());
        post.setCreatedAt(LocalDateTime.now());
        post.setStatus("DRAFT");
        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);

        // Handle media uploads
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            List<String> mediaUrls = new ArrayList<>();
            for (MultipartFile file : mediaFiles) {
                String mediaUrl = fileStorageService.storeFile(file);
                mediaUrls.add(mediaUrl);
            }
            post.setMediaUrls(mediaUrls);
        }

        Post savedPost = postRepository.save(post);
        return mapToPostResponse(savedPost);
    }

    public List<String> getCaptionSuggestions(String topic) {
        logger.info("Generating caption suggestions for topic: {}", topic);
        List<String> suggestions = new ArrayList<>();
        suggestions.add("Check out this amazing " + topic + "!");
        suggestions.add("Feeling inspired by " + topic + " today!");
        suggestions.add("What's your take on " + topic + "?");
        return suggestions;
    }

    public PostResponse getPostById(Long postId) {
        logger.info("Fetching post with ID: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToPostResponse(post);
    }

    private PostResponse mapToPostResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setContent(post.getContent());
        response.setMediaUrls(post.getMediaUrls());
        response.setVisibility(post.getVisibility());
        response.setCreatedAt(post.getCreatedAt());
        response.setScheduledAt(post.getScheduledAt());
        response.setStatus(post.getStatus());
        response.setUserId(post.getUser().getId());
        return response;
    }
}