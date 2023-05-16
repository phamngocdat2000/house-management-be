package com.student.searchroom.service;

import com.student.searchroom.entity.Comment;
import com.student.searchroom.entity.User;
import com.student.searchroom.event.NewCommentEvent;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.CommentAndCreatedAtInfo;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.request.CommentRequest;
import com.student.searchroom.model.response.CommentByPostResponse;
import com.student.searchroom.model.response.UserResponse;
import com.student.searchroom.repository.CommentRepository;
import com.student.searchroom.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initForeign() {
        try {
            jdbcTemplate.execute("ALTER TABLE search_room.comment ADD CONSTRAINT fk_comment_house FOREIGN KEY (post_id) REFERENCES search_room.house(id)");
            jdbcTemplate.execute("ALTER TABLE search_room.rating ADD CONSTRAINT fk_rating_user FOREIGN KEY (username) REFERENCES search_room.user(username)");
            jdbcTemplate.execute("ALTER TABLE search_room.house ADD CONSTRAINT fk_house_user FOREIGN KEY (created_by) REFERENCES search_room.user(username)");
        } catch (Exception e) {
            log.info("Created foreign!!!!!");
        }
    }

    public List<CommentByPostResponse> getCommentByPost(Long postId) {
        List<Comment> comments = commentRepository.getAllByPostId(postId);
        Map<String, UserResponse> userResponseMap = new HashMap<>();

        Set<String> usernames = new HashSet<>();
        comments.forEach(comment -> usernames.add(comment.getCreatedBy()));

        for (String username : usernames) {
            userResponseMap.put(username, UserResponse.from(userRepository.getById(username)));
        }

        List<CommentAndCreatedAtInfo> commentAndCreatedAtInfos = comments.stream()
                .map(comment -> comment.toCommentAndCreatedAtInfo(userResponseMap)).collect(Collectors.toList());


        List<CommentAndCreatedAtInfo> parentComments = commentAndCreatedAtInfos.stream().filter(comment -> comment.getParentId() == null).collect(Collectors.toList());
        List<CommentByPostResponse> result = new ArrayList<>();
        for (CommentAndCreatedAtInfo parentComment : parentComments) {
            List<CommentAndCreatedAtInfo> childComment = commentAndCreatedAtInfos.stream()
                    .filter(comment -> parentComment.getId().equals(comment.getParentId()))
                    .collect(Collectors.toList());
            result.add(CommentByPostResponse.from(parentComment, childComment));
        }
        return result;
    }


    public Comment addComment(String currentUser, CommentRequest commentReq, Long parentCommentId, Long postId) {
        Comment commentToSave = new Comment();
        commentToSave.setPostId(postId);
        commentToSave.setCreatedAt(new Date());
        commentToSave.setLastUpdateAt(new Date());
        commentToSave.setCreatedBy(currentUser);
        commentToSave.setContent(commentReq.getContent());
        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId).orElseThrow(() ->
                    new SearchRoomException(ErrorCode.PARENT_COMMENT_NOT_FOUND));
            commentToSave.setParentId(parentCommentId);
            commentToSave.setPostId(parentComment.getPostId());
        }
        Comment result = commentRepository.save(commentToSave);
        applicationEventPublisher.publishEvent(new NewCommentEvent(result));
        return result;
    }

    public Comment updateComment(String currentUser, CommentRequest commentRequest, Long commentId) {
        Comment commentToUpdate = commentRepository.findById(commentId).orElseThrow(() ->
                new SearchRoomException(ErrorCode.COMMENT_NOT_FOUND));
        commentToUpdate.setLastUpdateAt(new Date());
        commentToUpdate.setContent(commentRequest.getContent());
        if (!commentToUpdate.getCreatedBy().equals(currentUser))
            throw new SearchRoomException(ErrorCode.FORBIDDEN);
        return commentRepository.save(commentToUpdate);
    }

    public void deleteById(String currentUser, Long commentId) {
        Comment commentToDelete = commentRepository.findById(commentId).orElseThrow(() ->
                new SearchRoomException(ErrorCode.COMMENT_NOT_FOUND));
        if (!commentToDelete.getCreatedBy().equals(currentUser))
            throw new SearchRoomException(ErrorCode.FORBIDDEN);
        commentRepository.delete(commentToDelete);
    }
}
