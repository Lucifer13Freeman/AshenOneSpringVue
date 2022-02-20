package com.ashenone.AshenOne.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comments")
@JsonAutoDetect
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdContent.class)
    private Long id;

    @JsonView(Views.IdContent.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonView(Views.FullComment.class)
//    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    @JsonView(Views.IdContent.class)
    private User author;


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return id != null && Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
