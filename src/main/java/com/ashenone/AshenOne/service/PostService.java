package com.ashenone.AshenOne.service;

import com.ashenone.AshenOne.domain.Post;
import com.ashenone.AshenOne.domain.User;
import com.ashenone.AshenOne.domain.UserSubscription;
import com.ashenone.AshenOne.domain.Views;
import com.ashenone.AshenOne.dto.EventType;
import com.ashenone.AshenOne.dto.MetaDto;
import com.ashenone.AshenOne.dto.ObjectType;
import com.ashenone.AshenOne.dto.PostPageDto;
import com.ashenone.AshenOne.repo.PostRepo;
import com.ashenone.AshenOne.repo.UserSubscriptionRepo;
import com.ashenone.AshenOne.util.WsSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PostService
{
    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final PostRepo postRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final BiConsumer<EventType, Post> wsSender;


    @Autowired
    public PostService(
            PostRepo postRepo,
            UserSubscriptionRepo userSubscriptionRepo,
            WsSender wsSender
    ) {
        this.postRepo = postRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.wsSender = wsSender.getSender(ObjectType.POST, Views.FullPost.class);
    }

    private void fillMeta(Post post) throws IOException
    {
        String text = post.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find())
        {
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMAGE_REGEX.matcher(url);

            post.setLink(url);

            if (matcher.find())
            {
                post.setLinkCover(url);
            }
            else if (!url.contains("youtu"))
            {
                MetaDto meta = getMeta(url);

                post.setLinkCover(meta.getCover());
                post.setLinkTitle(meta.getTitle());
                post.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException
    {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element)
    {
        return element == null ? "" : element.attr("content");
    }

    public void delete(Post post)
    {
        postRepo.delete(post);
        wsSender.accept(EventType.DELETE, post);
    }

    public Post update(
            Post postFromDb,
            Post post
    ) throws IOException
    {
        postFromDb.setText(post.getText());
        fillMeta(postFromDb);
        Post updatedPost = postRepo.save(postFromDb);

        wsSender.accept(EventType.UPDATE, updatedPost);

        return updatedPost;
    }

    public Post create(Post post, User user) throws IOException
    {
        post.setCreatedAt(LocalDateTime.now());
        fillMeta(post);
        post.setAuthor(user);
        Post createdPost = postRepo.save(post);

        wsSender.accept(EventType.CREATE, createdPost);

        return createdPost;
    }

    public PostPageDto findAll(Pageable pageable)
    {
        Page<Post> page = postRepo.findAll(pageable);
        return new PostPageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

    public PostPageDto findForUser(Pageable pageable, User user)
    {
        List<User> profiles = userSubscriptionRepo.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getProfile)
                .collect(Collectors.toList());

        profiles.add(user);

        Page<Post> page = postRepo.findByAuthorIn(profiles, pageable);

        return new PostPageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

    public PostPageDto findByUser(Pageable pageable, User user)
    {
        Page<Post> page = postRepo.findByAuthor(user, pageable);

        return new PostPageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }
}
