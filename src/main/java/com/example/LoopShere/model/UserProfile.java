package com.example.LoopShere.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_profiles")  // Optional: specify table name
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String bio;
    private String phoneNumber;
    private String profilePhotoUrl;
    private String chatTheme;
    private boolean notificationsEnabled;
    private int disappearingMessagesDuration;

    @ElementCollection
    private List<Long> blockedContacts;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getChatTheme() {
        return chatTheme;
    }

    public void setChatTheme(String chatTheme) {
        this.chatTheme = chatTheme;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public int getDisappearingMessagesDuration() {
        return disappearingMessagesDuration;
    }

    public void setDisappearingMessagesDuration(int disappearingMessagesDuration) {
        this.disappearingMessagesDuration = disappearingMessagesDuration;
    }

    public List<Long> getBlockedContacts() {
        return blockedContacts;
    }

    public void setBlockedContacts(List<Long> blockedContacts) {
        this.blockedContacts = blockedContacts;
    }
}
