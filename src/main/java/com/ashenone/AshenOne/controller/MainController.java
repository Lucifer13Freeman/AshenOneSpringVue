package com.ashenone.AshenOne.controller;

import com.ashenone.AshenOne.domain.User;
import com.ashenone.AshenOne.domain.Views;
import com.ashenone.AshenOne.dto.PostPageDto;
import com.ashenone.AshenOne.repo.UserRepo;
import com.ashenone.AshenOne.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController
{
    private final PostService postService;
    private final UserRepo userRepo;

    private final ObjectWriter postWriter;
    private final ObjectWriter profileWriter;

    @Value("${spring.profiles.active:prod}")
    private String mode;

    @Autowired
    public MainController(
            PostService postService,
            UserRepo userRepo,
            ObjectMapper mapper
    ) {
        this.postService = postService;
        this.userRepo = userRepo;

        ObjectMapper objectMapper = mapper
                .setConfig(mapper.getSerializationConfig());

        this.postWriter = objectMapper
                .writerWithView(Views.FullPost.class);
        this.profileWriter = objectMapper
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(
            Model model,
            @AuthenticationPrincipal User user
    ) throws JsonProcessingException
    {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null)
        {
            User userFromDb = userRepo.findById(user.getId()).get();
            String serializedProfile = profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", serializedProfile);

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, PostController.POSTS_PER_PAGE, sort);

            PostPageDto postPageDto = postService.findAll(pageRequest);
            PostPageDto newsPostsPageDto = postService.findForUser(pageRequest, user);
//            PostPageDto userPostsPageDto = postService.findByUser(pageRequest, user);

            String posts = postWriter.writeValueAsString(postPageDto.getPosts());
            String newsPosts = postWriter.writeValueAsString(newsPostsPageDto.getPosts());
//            String userPosts = postWriter.writeValueAsString(userPostsPageDto.getPosts());

            model.addAttribute("posts", posts);
            model.addAttribute("newsPosts", newsPosts);
//            model.addAttribute("userPosts", userPosts);

            data.put("currentPage", postPageDto.getCurrentPage());
            data.put("totalPages", postPageDto.getTotalPages());

            data.put("currentNewsPage", newsPostsPageDto.getCurrentPage());
            data.put("totalNewsPages", newsPostsPageDto.getTotalPages());

//            data.put("currentUserPage", userPostsPageDto.getCurrentPage());
//            data.put("totalUserPages", userPostsPageDto.getTotalPages());
        }
        else
        {
            model.addAttribute("posts", "[]");
            model.addAttribute("newsPosts", "[]");
//            model.addAttribute("userPosts", "[]");
            model.addAttribute("profile", "null");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(mode));

        return "index";
    }
}
