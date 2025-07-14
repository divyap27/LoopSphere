package com.example.LoopShere.repository;

import com.example.LoopShere.model.Comments;
import com.example.LoopShere.model.Comments;
import com.example.LoopShere.model.Post;
import com.example.LoopShere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByPostAndParentCommentIsNull(Post post);
    List<Comments> findByParentComment(Comments parentComment);
    List<Comments> findByPostAndIsReportedTrue(Post post);
}
