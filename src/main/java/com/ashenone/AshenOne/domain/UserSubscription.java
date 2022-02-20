package com.ashenone.AshenOne.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_subscriptions")
@Getter
@Setter
@RequiredArgsConstructor
@ToString(of = "id")
public class UserSubscription implements Serializable
{
    @EmbeddedId
    @JsonIgnore
    private UserSubscriptionId id;

    @MapsId("profileId")
    @ManyToOne
    @JsonView(Views.IdContent.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User profile;

    @MapsId("subscriberId")
    @ManyToOne
    @JsonView(Views.IdContent.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User subscriber;

    @Column(name = "is_active")
    @JsonView(Views.IdContent.class)
    private boolean active;

    public UserSubscription(User profile, User subscriber)
    {
        this.profile = profile;
        this.subscriber = subscriber;
        this.id = new UserSubscriptionId(profile.getId(), subscriber.getId());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserSubscription that = (UserSubscription) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}