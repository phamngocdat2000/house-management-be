package com.student.searchroom.controller;

import com.student.searchroom.entity.Comment;
import com.student.searchroom.model.request.CommentRequest;
import com.student.searchroom.model.response.CommentByPostResponse;
import com.student.searchroom.security.SecurityUtil;
import com.student.searchroom.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> registerComment(@RequestBody @Valid CommentRequest commentRequest,
                                                   @PathVariable Long postId) {
        String currentUser = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.addComment(currentUser, commentRequest, null, postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@RequestBody @Valid CommentRequest commentRequest,
                                                 @PathVariable Long commentId) {
        String currentUser = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.updateComment(currentUser, commentRequest, commentId));
    }

    @PostMapping("/child/{parentCommentId}")
    public ResponseEntity<Comment> registerChildComment(@RequestBody @Valid CommentRequest commentRequest,
                                                   @PathVariable Long parentCommentId) {
        String currentUser = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.addComment(currentUser, commentRequest, parentCommentId, null));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentByPostResponse>> getCommentByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentByPost(postId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long commentId) {
        String currentUser = SecurityUtil.getCurrentUsername();
        commentService.deleteById(currentUser, commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
