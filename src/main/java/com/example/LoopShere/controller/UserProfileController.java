package com.example.LoopShere.controller;
import com.example.LoopShere.service.UserProfileService;
import com.example.LoopShere.model.UserProfile;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/user-profiles")
public class UserProfileController {
@Autowired
    private UserProfileService userProfileService;

    @PostMapping("/create")
    public ResponseEntity<UserProfile> createOrUpdateProfile(@Validated @RequestBody UserProfile userProfile) {
        UserProfile savedProfile = userProfileService.createOrUpdateProfile(userProfile);
        return ResponseEntity.ok(savedProfile);
    }

 
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable Long userId) {
        UserProfile profile = userProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

   
    @PutMapping("/{userId}/bio")
    public ResponseEntity<UserProfile> updateBio(@PathVariable Long userId, @RequestBody String bio) {
        UserProfile updatedProfile = userProfileService.updateBio(userId, bio);
        return ResponseEntity.ok(updatedProfile);
    }


    @PutMapping("/{userId}/password")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @RequestBody String newPassword) {
        userProfileService.changePassword(userId, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

  
    @PutMapping("/{userId}/phone")
    public ResponseEntity<UserProfile> changePhoneNumber(@PathVariable Long userId, @RequestBody String newPhoneNumber) {
        UserProfile updatedProfile = userProfileService.changePhoneNumber(userId, newPhoneNumber);
        return ResponseEntity.ok(updatedProfile);
    }

    // Block Contact (Privacy)
    @PostMapping("/{userId}/block")
    public ResponseEntity<String> blockContact(@PathVariable Long userId, @RequestBody Long contactId) {
        userProfileService.blockContact(userId, contactId);
        return ResponseEntity.ok("Contact blocked successfully");
    }

    // Set Disappearing Messages (Privacy)
    @PutMapping("/{userId}/disappearing-messages")
    public ResponseEntity<String> setDisappearingMessages(@PathVariable Long userId, @RequestBody int durationHours) {
        userProfileService.setDisappearingMessages(userId, durationHours);
        return ResponseEntity.ok("Disappearing messages set to " + durationHours + " hours");
    }

    // Update Profile Photo
    @PutMapping("/{userId}/profile-photo")
    public ResponseEntity<UserProfile> updateProfilePhoto(@PathVariable Long userId, @RequestBody String photoUrl) {
        UserProfile updatedProfile = userProfileService.updateProfilePhoto(userId, photoUrl);
        return ResponseEntity.ok(updatedProfile);
    }

    // Update Chat Theme/Wallpaper
    @PutMapping("/{userId}/chat-theme")
    public ResponseEntity<UserProfile> updateChatTheme(@PathVariable Long userId, @RequestBody String theme) {
        UserProfile updatedProfile = userProfileService.updateChatTheme(userId, theme);
        return ResponseEntity.ok(updatedProfile);
    }

    // Export Chat History
    @GetMapping("/{userId}/chat-history")
    public ResponseEntity<String> exportChatHistory(@PathVariable Long userId) {
        String chatHistory = userProfileService.exportChatHistory(userId);
        return ResponseEntity.ok(chatHistory);
    }

    // Update Notification Settings
    @PutMapping("/{userId}/notifications")
    public ResponseEntity<UserProfile> updateNotifications(@PathVariable Long userId, @RequestBody boolean enabled) {
        UserProfile updatedProfile = userProfileService.updateNotifications(userId, enabled);
        return ResponseEntity.ok(updatedProfile);
    }

    // Invite a Friend
    @PostMapping("/{userId}/invite")
    public ResponseEntity<String> inviteFriend(@PathVariable Long userId, @RequestBody String email) {
        userProfileService.inviteFriend(userId, email);
        return ResponseEntity.ok("Invitation sent to " + email);
    }
}
