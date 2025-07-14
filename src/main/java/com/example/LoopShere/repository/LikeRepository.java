package com.example.LoopShere.repository;

import com.example.LoopShere.model.Like;
import com.example.LoopShere.model.Post;
import com.example.LoopShere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
 Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findByPost(Post post);
}
