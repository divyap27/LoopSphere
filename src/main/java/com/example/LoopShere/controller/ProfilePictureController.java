package com.example.LoopShere.controller;
import com.example.LoopShere.model.ProfilePicture;
import com.example.LoopShere.service.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile-picture")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) {
         try {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.ok("No image uploaded. You can choose to use a default image.");
        }

        service.saveOrUpdateProfilePicture(file, userId);
        return ResponseEntity.ok("Uploaded successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Upload failed: " + e.getMessage());
    }
}

@GetMapping("/{userId}")
public ResponseEntity<?> getProfilePicture(@PathVariable Long userId) {
    System.out.println("GET profile picture called for userId = " + userId);

    Optional<ProfilePicture> pictureOpt = service.getProfilePictureByUserId(userId);

    if (pictureOpt.isPresent()) {
        ProfilePicture picture = pictureOpt.get();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(picture.getContentType()))
                .body(picture.getData());
    }

    
    return ResponseEntity.ok("User has not uploaded a profile picture. You can choose a default image.");
}

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteProfilePicture(@PathVariable Long userId) {
        String result = service.deleteProfilePictureByUserId(userId);

        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.contains("No profile")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
