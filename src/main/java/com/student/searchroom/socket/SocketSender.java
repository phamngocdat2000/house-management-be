package com.student.searchroom.socket;

import com.student.searchroom.entity.Comment;
import com.student.searchroom.socket.model.CommentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketSender {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private static final String PREFIX_TOPIC = "/post/";

    public void sendCommentToClient(String postId, Comment comment, String type) {
        CommentEvent commentEvent = new CommentEvent(comment, type);
        simpMessagingTemplate.convertAndSend(PREFIX_TOPIC + postId, commentEvent);
    }


}
