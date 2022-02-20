package com.ashenone.AshenOne.repo;

import com.ashenone.AshenOne.domain.User;
import com.ashenone.AshenOne.domain.UserSubscription;
import com.ashenone.AshenOne.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription, UserSubscriptionId>
{
    List<UserSubscription> findBySubscriber(User user);

    List<UserSubscription> findByProfile(User profile);

    UserSubscription findByProfileAndSubscriber(User profile, User subscriber);
}
