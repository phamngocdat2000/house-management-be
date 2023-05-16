package com.student.searchroom.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRequest {
    @NotBlank(message = "content is required!")
    private String content;
}
