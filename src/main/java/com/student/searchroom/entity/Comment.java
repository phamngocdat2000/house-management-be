package com.student.searchroom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.student.searchroom.model.CommentAndCreatedAtInfo;
import com.student.searchroom.model.response.UserResponse;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;
import java.util.Map;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private Long postId;
    private String createdBy;
    @Lob
    private String content;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date lastUpdateAt;
    private Long parentId;
    public CommentAndCreatedAtInfo toCommentAndCreatedAtInfo(Map<String, UserResponse> userResponseMap) {
        CommentAndCreatedAtInfo result = new CommentAndCreatedAtInfo();
        BeanUtils.copyProperties(this, result);
        result.setCreatedByInfo(userResponseMap.get(this.createdBy));
        return result;
    }

}
