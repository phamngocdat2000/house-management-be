package com.student.searchroom.socket;

import com.student.searchroom.entity.Comment;
import com.student.searchroom.event.NewCommentEvent;
import com.student.searchroom.event.UpdateCommentEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CommentHandle {

    @Autowired
    private SocketSender socketSender;

    @EventListener
    @Async
    public void handleNewComment(NewCommentEvent newCommentEvent) {
        Comment comment = (Comment) newCommentEvent.getSource();
        socketSender.sendCommentToClient(comment.getPostId() + "", comment, "NEW");
        log.info("Handle new comment id {} done!!", comment.getId());
    }

    @EventListener
    @Async
    public void handleUpdateComment(UpdateCommentEvent updateCommentEvent) {
        Comment comment = (Comment) updateCommentEvent.getSource();
        socketSender.sendCommentToClient(comment.getPostId() + "", comment, "UPDATE");
        log.info("Handle update comment id {} done!!", comment.getId());
    }

}
