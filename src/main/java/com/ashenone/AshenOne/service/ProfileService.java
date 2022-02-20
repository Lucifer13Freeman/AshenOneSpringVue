package com.ashenone.AshenOne.service;

import com.ashenone.AshenOne.domain.User;
import com.ashenone.AshenOne.domain.UserSubscription;
import com.ashenone.AshenOne.repo.UserRepo;
import com.ashenone.AshenOne.repo.UserSubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService 
{
    private final UserRepo userRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;

    @Autowired
    public ProfileService(
            UserRepo userRepo,
            UserSubscriptionRepo userSubscriptionRepo
    ) {
        this.userRepo = userRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
    }

    public User changeSubscription(User profile, User subscriber) 
    {
        List<UserSubscription> subscriptions = profile.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().equals(subscriber)
                )
                .collect(Collectors.toList());

        if (subscriptions.isEmpty())
        {
            UserSubscription subscription = new UserSubscription(profile, subscriber);
            profile.getSubscribers().add(subscription);
        }
        else
        {
            subscriptions.forEach(profile.getSubscribers()::remove);
        }

        return userRepo.save(profile);
    }

    public List<UserSubscription> getSubscribers(User profile)
    {
        return userSubscriptionRepo.findByProfile(profile);
    }

    public UserSubscription changeSubscriptionStatus(User profile, User subscriber)
    {
        UserSubscription subscription = userSubscriptionRepo.findByProfileAndSubscriber(profile, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepo.save(subscription);
    }
}
