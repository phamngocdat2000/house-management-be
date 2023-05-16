package com.student.searchroom.event;

import com.student.searchroom.entity.Comment;
import org.springframework.context.ApplicationEvent;

public class UpdateCommentEvent extends ApplicationEvent {

    public UpdateCommentEvent(Comment comment) {
        super(comment);
    }
}
