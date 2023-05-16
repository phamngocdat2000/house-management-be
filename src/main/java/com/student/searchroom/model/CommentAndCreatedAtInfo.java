package com.student.searchroom.model;

import com.student.searchroom.entity.Comment;
import com.student.searchroom.model.response.UserResponse;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Lob;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class CommentAndCreatedAtInfo {
    private Long id;
    private Long postId;
    private String content;
    private Date createdAt;
    private Date lastUpdateAt;
    private Long parentId;
    private UserResponse createdByInfo;


}
