package com.student.searchroom.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.student.searchroom.entity.Comment;
import com.student.searchroom.model.CommentAndCreatedAtInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
public class CommentByPostResponse {
    private Long id;
    private Long postId;
    private UserResponse createdByInfo;
    private String content;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date lastUpdateAt;
    private Long parentId;
    private List<CommentAndCreatedAtInfo> childComment;

    public static CommentByPostResponse from(CommentAndCreatedAtInfo comment, List<CommentAndCreatedAtInfo> childComment) {
        CommentByPostResponse commentByPostResponse = new CommentByPostResponse();
        BeanUtils.copyProperties(comment, commentByPostResponse);
        commentByPostResponse.setChildComment(childComment);
        return commentByPostResponse;
    }
}
