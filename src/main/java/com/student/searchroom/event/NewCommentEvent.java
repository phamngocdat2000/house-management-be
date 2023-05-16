package com.student.searchroom.event;

import com.student.searchroom.entity.Comment;
import org.springframework.context.ApplicationEvent;

public class NewCommentEvent extends ApplicationEvent {
    public NewCommentEvent(Comment comment) {
        super(comment);
    }
}
