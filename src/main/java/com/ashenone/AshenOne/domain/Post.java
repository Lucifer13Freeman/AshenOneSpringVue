package com.ashenone.AshenOne.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "posts")
@JsonAutoDetect
@Getter
@Setter
@ToString(of = {"id", "text"})
@RequiredArgsConstructor
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdContent.class)
    private String text;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullPost.class)
    private User author;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @JsonView(Views.FullPost.class)
//    @JsonManagedReference
    private List<Comment> comments;

    @Column(updatable = false)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonView(Views.FullPost.class)
    private LocalDateTime createdAt;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonView(Views.FullPost.class)
    private LocalDateTime updatedAt;

    @JsonView(Views.FullPost.class)
    private String link;

    @JsonView(Views.FullPost.class)
    private String linkTitle;

    @JsonView(Views.FullPost.class)
    private String linkDescription;

    @JsonView(Views.FullPost.class)
    private String linkCover;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
