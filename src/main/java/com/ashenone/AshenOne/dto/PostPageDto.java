package com.ashenone.AshenOne.dto;

import com.ashenone.AshenOne.domain.Post;
import com.ashenone.AshenOne.domain.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.FullPost.class)
public class PostPageDto
{
    private List<Post> posts;
    private int currentPage;
    private int totalPages;
}
