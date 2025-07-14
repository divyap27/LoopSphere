package com.example.LoopShere.model;

import jakarta.persistence.*;

@Entity
@Table(name = "group_members")
@IdClass(GroupMemberId.class)
public class GroupMember {

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Id
    @Column(name = "user_email")
    private String userEmail;

    // Getters and setters
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}