package com.ashenone.AshenOne.repo;

import com.ashenone.AshenOne.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> { }
