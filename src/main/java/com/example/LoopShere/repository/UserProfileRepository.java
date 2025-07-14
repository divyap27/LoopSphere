package com.example.LoopShere.repository;
import com.example.LoopShere.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);

}
