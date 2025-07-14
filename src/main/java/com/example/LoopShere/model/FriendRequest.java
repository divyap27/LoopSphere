package com.example.LoopShere.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class FriendRequest {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User sender;
    
    @ManyToOne
    private User receiver;
    
    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;
    
    private LocalDateTime createdAt;

    public class FriendRequestStatus {

        public static FriendRequestStatus PENDING;
        public static FriendRequestStatus REJECTED;
        public static FriendRequestStatus ACCEPTED;
    }

    public void setRecipient(User recipient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRecipient'");
    }

    public void setFriendRequestStatus(FriendRequestStatus pENDING) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFriendRequestStatus'");
    }

    public User getRecipient() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRecipient'");
    }

    public FriendRequestStatus getFriendRequestStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFriendRequestStatus'");
    }
}

enum FriendRequestStatus {
    PENDING, ACCEPTED, REJECTED
}

