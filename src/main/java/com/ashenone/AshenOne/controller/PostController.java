package com.ashenone.AshenOne.controller;

import com.ashenone.AshenOne.domain.Post;
import com.ashenone.AshenOne.domain.User;
import com.ashenone.AshenOne.domain.Views;
import com.ashenone.AshenOne.dto.PostPageDto;
import com.ashenone.AshenOne.service.PostService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("post")
public class PostController
{
    public static final int POSTS_PER_PAGE = 5;

    private final PostService postService;

    @Autowired
    public PostController(PostService postService)
    {
        this.postService = postService;
    }


    @GetMapping()
    @JsonView(Views.FullPost.class)
    public PostPageDto getPosts(
            @PageableDefault(
                    size = POSTS_PER_PAGE,
                    sort = { "id" },
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return postService.findAll(pageable);
    }

    @GetMapping("news")
    @JsonView(Views.FullPost.class)
    public PostPageDto getPostsForUser(
            @AuthenticationPrincipal User user,
            @PageableDefault(
                    size = POSTS_PER_PAGE,
                    sort = { "id" },
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return postService.findForUser(pageable, user);
    }

    @GetMapping("profile/{authorId}")
    @JsonView(Views.FullPost.class)
    public PostPageDto getUserPosts(
            @PathVariable("authorId") User user,
            @PageableDefault(
                    size = POSTS_PER_PAGE,
                    sort = { "id" },
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return postService.findByUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullPost.class)
    public Post getOne(@PathVariable("id") Post post)
    {
        return post;
    }

    @PostMapping
    @JsonView(Views.FullPost.class)
    public Post create(
            @RequestBody Post post,
            @AuthenticationPrincipal User user
    ) throws IOException
    {
        return postService.create(post, user);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullPost.class)
    public Post update(
            @PathVariable("id") Post postFromDb,
            @RequestBody Post post,
            @AuthenticationPrincipal User author
    ) throws IOException
    {
        return postFromDb.getAuthor().equals(author)
                    ? postService.update(postFromDb, post)
                    : postFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Post post)
    {
        postService.delete(post);
    }
}
