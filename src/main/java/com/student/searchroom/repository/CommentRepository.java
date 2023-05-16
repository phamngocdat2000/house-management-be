package com.student.searchroom.repository;

import com.student.searchroom.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.postId = :postId ORDER BY c.createdAt")
    List<Comment> getAllByPostId(@Param(value = "postId") Long postId);
}
