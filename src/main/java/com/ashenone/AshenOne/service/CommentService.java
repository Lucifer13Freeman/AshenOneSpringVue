package com.ashenone.AshenOne.service;

import com.ashenone.AshenOne.domain.Comment;
import com.ashenone.AshenOne.domain.User;
import com.ashenone.AshenOne.domain.Views;
import com.ashenone.AshenOne.dto.EventType;
import com.ashenone.AshenOne.dto.ObjectType;
import com.ashenone.AshenOne.repo.CommentRepo;
import com.ashenone.AshenOne.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService
{
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, WsSender wsSender)
    {
        this.commentRepo = commentRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user)
    {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepo.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}