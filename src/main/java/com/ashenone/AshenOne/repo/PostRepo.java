package com.ashenone.AshenOne.repo;

import com.ashenone.AshenOne.domain.Post;
import com.ashenone.AshenOne.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long>
{
    @EntityGraph(attributePaths = { "comments" })
    Page<Post> findByAuthorIn(List<User> users, Pageable pageable);

    @EntityGraph(attributePaths = { "comments" })
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(attributePaths = { "comments" })
    Page<Post> findByAuthor(User user, Pageable pageable);
}
