package com.example.LoopShere.service;
import com.example.LoopShere.model.ProfilePicture;
import com.example.LoopShere.model.User;
import com.example.LoopShere.repository.ProfilePictureRepository;
import com.example.LoopShere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProfilePictureService {
  @Autowired
    private ProfilePictureRepository pictureRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveOrUpdateProfilePicture(MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ProfilePicture existing = user.getProfilePicture() != null ? user.getProfilePicture() : new ProfilePicture();

        existing.setFileName(file.getOriginalFilename());
        existing.setContentType(file.getContentType());
        existing.setData(file.getBytes());

        pictureRepository.save(existing);

        user.setProfilePicture(existing);
        userRepository.save(user);
    }

    public Optional<ProfilePicture> getProfilePictureByUserId(Long userId) {
        return userRepository.findById(userId).map(User::getProfilePicture);
    }

    public String deleteProfilePictureByUserId(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return "User not found";

        User user = userOpt.get();
        ProfilePicture picture = user.getProfilePicture();
        if (picture == null) return "No profile picture to delete";

        user.setProfilePicture(null);
        userRepository.save(user);
        pictureRepository.delete(picture);
        return "Profile picture deleted successfully";
    }
}
