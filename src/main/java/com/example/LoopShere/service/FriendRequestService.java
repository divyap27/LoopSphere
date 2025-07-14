package com.example.LoopShere.service;
import com.example.LoopShere.model.FriendRequest;
import com.example.LoopShere.model.User;
import com.example.LoopShere.repository.FriendRequestRepository;
import com.example.LoopShere.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FriendRequestService {


    private static final Logger logger = LoggerFactory.getLogger(FriendRequestService.class);

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FriendRequest sendFriendRequest(String senderEmail, String recipientEmail) {
        logger.info("Sending friend request from {} to {}", senderEmail, recipientEmail);

        if (senderEmail.equals(recipientEmail)) {
            logger.error("Sender and recipient cannot be the same");
            throw new IllegalArgumentException("Cannot send friend request to yourself");
        }

        User sender = userRepository.findByEmail(senderEmail)
            .orElseThrow(() -> {
                logger.error("Sender not found: {}", senderEmail);
                return new IllegalArgumentException("Sender not found: " + senderEmail);
            });

        User recipient = userRepository.findByEmail(recipientEmail)
            .orElseThrow(() -> {
                logger.error("Recipient not found: {}", recipientEmail);
                return new IllegalArgumentException("Recipient not found: " + recipientEmail);
            });

        Optional<FriendRequest> existingRequest = friendRequestRepository.findBySenderAndRecipientAndFriendRequestStatus(
            sender, recipient, FriendRequest.FriendRequestStatus.PENDING);
        if (existingRequest.isPresent()) {
            logger.error("Pending friend request already exists from {} to {}", senderEmail, recipientEmail);
            throw new IllegalArgumentException("Friend request already sent");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setRecipient(recipient);
        friendRequest.setFriendRequestStatus(FriendRequest.FriendRequestStatus.PENDING);

        FriendRequest savedRequest = friendRequestRepository.save(friendRequest);
        logger.info("Friend request saved with ID: {}", savedRequest.getId());
        return savedRequest;
    }

    @Transactional
    public FriendRequest acceptFriendRequest(Long requestId, String recipientEmail) {
        logger.info("Accepting friend request ID: {} for recipient: {}", requestId, recipientEmail);

        FriendRequest request = friendRequestRepository.findById(requestId)
            .orElseThrow(() -> {
                logger.error("Friend request not found: {}", requestId);
                return new IllegalArgumentException("Friend request not found");
            });

        if (!request.getRecipient().getEmail().equals(recipientEmail)) {
            logger.error("Recipient {} is not authorized to accept request ID: {}", recipientEmail, requestId);
            throw new IllegalArgumentException("Not authorized to accept this request");
        }

        if (request.getFriendRequestStatus() != FriendRequest.FriendRequestStatus.PENDING) {
            logger.error("Request ID: {} is not pending", requestId);
            throw new IllegalArgumentException("Request is not pending");
        }

        request.setFriendRequestStatus(FriendRequest.FriendRequestStatus.ACCEPTED);
        FriendRequest updatedRequest = friendRequestRepository.save(request);
        logger.info("Friend request ID: {} accepted", requestId);
        return updatedRequest;
    }

    @Transactional
    public void rejectFriendRequest(Long requestId, String recipientEmail) {
        logger.info("Rejecting friend request ID: {} for recipient: {}", requestId, recipientEmail);

        FriendRequest request = friendRequestRepository.findById(requestId)
            .orElseThrow(() -> {
                logger.error("Friend request not found: {}", requestId);
                return new IllegalArgumentException("Friend request not found");
            });

        if (!request.getRecipient().getEmail().equals(recipientEmail)) {
            logger.error("Recipient {} is not authorized to reject request ID: {}", recipientEmail, requestId);
            throw new IllegalArgumentException("Not authorized to reject this request");
        }

        if (request.getFriendRequestStatus() != FriendRequest.FriendRequestStatus.PENDING) {
            logger.error("Request ID: {} is not pending", requestId);
            throw new IllegalArgumentException("Request is not pending");
        }

        request.setFriendRequestStatus(FriendRequest.FriendRequestStatus.REJECTED);
        friendRequestRepository.save(request);
        logger.info("Friend request ID: {} rejected", requestId);
    }

    public List<FriendRequest> getPendingReceivedRequests(String recipientEmail) {
        logger.info("Fetching pending friend requests for recipient: {}", recipientEmail);
        User recipient = userRepository.findByEmail(recipientEmail)
            .orElseThrow(() -> {
                logger.error("Recipient not found: {}", recipientEmail);
                return new IllegalArgumentException("Recipient not found: " + recipientEmail);
            });
        return friendRequestRepository.findByRecipientAndFriendRequestStatus(
            recipient, FriendRequest.FriendRequestStatus.PENDING);
    }

    public List<FriendRequest> getPendingSentRequests(String senderEmail) {
        logger.info("Fetching pending sent friend requests for sender: {}", senderEmail);
        User sender = userRepository.findByEmail(senderEmail)
            .orElseThrow(() -> {
                logger.error("Sender not found: {}", senderEmail);
                return new IllegalArgumentException("Sender not found: " + senderEmail);
            });
        return friendRequestRepository.findBySenderAndFriendRequestStatus(
            sender, FriendRequest.FriendRequestStatus.PENDING);
    
}
}
