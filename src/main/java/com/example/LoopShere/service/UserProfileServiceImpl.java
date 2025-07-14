package com.example.LoopShere.service;

import com.example.LoopShere.model.UserProfile;
import com.example.LoopShere.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfile createOrUpdateProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile getProfile(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found for user: " + userId));
    }

    @Override
    public UserProfile updateBio(Long userId, String bio) {
        UserProfile profile = getProfile(userId);
        profile.setBio(bio);
        return userProfileRepository.save(profile);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        // This is a placeholder — password should be managed in User entity/service
    }

    @Override
    public UserProfile changePhoneNumber(Long userId, String newPhoneNumber) {
        UserProfile profile = getProfile(userId);
        profile.setPhoneNumber(newPhoneNumber);
        return userProfileRepository.save(profile);
    }

    @Override
    public void blockContact(Long userId, Long contactId) {
        UserProfile profile = getProfile(userId);
        List<Long> blockedContacts = profile.getBlockedContacts();
        if (blockedContacts == null) {
            blockedContacts = new ArrayList<>();
        }
        if (!blockedContacts.contains(contactId)) {
            blockedContacts.add(contactId);
            profile.setBlockedContacts(blockedContacts);
            userProfileRepository.save(profile);
        }
    }

    @Override
    public void setDisappearingMessages(Long userId, int durationHours) {
        UserProfile profile = getProfile(userId);
        profile.setDisappearingMessagesDuration(durationHours);
        userProfileRepository.save(profile);
    }

    @Override
    public UserProfile updateProfilePhoto(Long userId, String photoUrl) {
        UserProfile profile = getProfile(userId);
        profile.setProfilePhotoUrl(photoUrl);
        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile updateChatTheme(Long userId, String theme) {
        UserProfile profile = getProfile(userId);
        profile.setChatTheme(theme);
        return userProfileRepository.save(profile);
    }

    @Override
    public String exportChatHistory(Long userId) {
        return "Chat history for user " + userId;
    }

    @Override
    public UserProfile updateNotifications(Long userId, boolean enabled) {
        UserProfile profile = getProfile(userId);
        profile.setNotificationsEnabled(enabled);
        return userProfileRepository.save(profile);
    }

    @Override
    public void inviteFriend(Long userId, String email) {
        System.out.println("Sending invitation to " + email + " from user ID " + userId);
    }
}
