package com.student.searchroom.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRequest {
    @NotBlank(message = "Không được để trống nội dung bình luận!")
    private String content;
}
