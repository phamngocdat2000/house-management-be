package com.student.searchroom.socket.model;

import com.student.searchroom.entity.Comment;
import lombok.Data;

@Data
public class CommentEvent {
    private Comment comment;
    private String type;

    public CommentEvent(Comment comment, String type) {
        this.comment = comment;
        this.type = type;
    }
}
