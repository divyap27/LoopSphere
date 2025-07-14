package com.example.LoopShere.service;

import com.example.LoopShere.model.UserProfile;

public interface UserProfileService {
    UserProfile createOrUpdateProfile(UserProfile profile);
    UserProfile getProfile(Long userId);
    UserProfile updateBio(Long userId, String bio);
    void changePassword(Long userId, String newPassword);
    UserProfile changePhoneNumber(Long userId, String newPhoneNumber);
    void blockContact(Long userId, Long contactId);
    void setDisappearingMessages(Long userId, int durationHours);
    UserProfile updateProfilePhoto(Long userId, String photoUrl);
    UserProfile updateChatTheme(Long userId, String theme);
    String exportChatHistory(Long userId);
    UserProfile updateNotifications(Long userId, boolean enabled);
    void inviteFriend(Long userId, String email);
}
