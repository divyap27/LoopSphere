package com.example.LoopShere.repository;
import com.example.LoopShere.model.FriendRequest;
import com.example.LoopShere.model.FriendRequest.FriendRequestStatus;
import com.example.LoopShere.model.FriendRequest;
import com.example.LoopShere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
 List<FriendRequest> findByReceiverAndStatus(User receiver, FriendRequest status);
    List<FriendRequest> findBySenderAndReceiverAndStatus(User sender, User receiver, FriendRequest status);
    Optional<FriendRequest> findBySenderAndRecipientAndFriendRequestStatus(
        User sender, User recipient, FriendRequest.FriendRequestStatus status);
    List<FriendRequest> findByRecipientAndFriendRequestStatus(User recipient, FriendRequestStatus pENDING);
    List<FriendRequest> findBySenderAndFriendRequestStatus(User sender, FriendRequestStatus pENDING);
}
