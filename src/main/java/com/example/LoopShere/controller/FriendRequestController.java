package com.example.LoopShere.controller;

import com.example.LoopShere.model.FriendRequest;
import com.example.LoopShere.service.FriendRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

    private static final Logger logger = LoggerFactory.getLogger(FriendRequestController.class);
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/send")
    public ResponseEntity<FriendRequest> sendFriendRequest(@RequestBody Map<String, String> request) {
        String senderEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        String recipientEmail = request.get("recipientEmail");
        logger.info("Processing friend request from {} to {}", senderEmail, recipientEmail);
        try {
            FriendRequest friendRequest = friendRequestService.sendFriendRequest(senderEmail, recipientEmail);
            return ResponseEntity.status(HttpStatus.CREATED).body(friendRequest);
        } catch (IllegalArgumentException e) {
            logger.error("Error sending friend request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<FriendRequest> acceptFriendRequest(@PathVariable Long id) {
        String recipientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Processing accept friend request ID: {} for {}", id, recipientEmail);
        try {
            FriendRequest updatedRequest = friendRequestService.acceptFriendRequest(id, recipientEmail);
            return ResponseEntity.ok(updatedRequest);
        } catch (IllegalArgumentException e) {
            logger.error("Error accepting friend request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable Long id) {
        String recipientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Processing reject friend request ID: {} for {}", id, recipientEmail);
        try {
            friendRequestService.rejectFriendRequest(id, recipientEmail);
            return ResponseEntity.ok("Friend request rejected");
        } catch (IllegalArgumentException e) {
            logger.error("Error rejecting friend request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/received/pending")
    public ResponseEntity<List<FriendRequest>> getPendingReceivedRequests() {
        String recipientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Fetching pending received friend requests for {}", recipientEmail);
        try {
            List<FriendRequest> requests = friendRequestService.getPendingReceivedRequests(recipientEmail);
            return ResponseEntity.ok(requests);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching pending requests: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/sent/pending")
    public ResponseEntity<List<FriendRequest>> getPendingSentRequests() {
        String senderEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Fetching pending sent friend requests for {}", senderEmail);
        try {
            List<FriendRequest> requests = friendRequestService.getPendingSentRequests(senderEmail);
            return ResponseEntity.ok(requests);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching pending sent requests: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
