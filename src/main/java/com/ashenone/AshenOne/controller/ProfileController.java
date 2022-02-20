package com.ashenone.AshenOne.controller;

import com.ashenone.AshenOne.domain.User;
import com.ashenone.AshenOne.domain.UserSubscription;
import com.ashenone.AshenOne.domain.Views;
import com.ashenone.AshenOne.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController
{
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService)
    {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user)
    {
        return user;
    }

    @PostMapping("subscription/{profileId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(
            @AuthenticationPrincipal User subscriber,
            @PathVariable("profileId") User profile
    ) {
        if (subscriber.equals(profile)) return profile;
        else return profileService.changeSubscription(profile, subscriber);
    }

    @GetMapping("subscribers/{profileId}")
    @JsonView(Views.IdContent.class)
    public List<UserSubscription> subscribers(
            @PathVariable("profileId") User profile
    ) {
        return profileService.getSubscribers(profile);
    }

    @PostMapping("subscription-status/{subscriberId}")
    @JsonView(Views.IdContent.class)
    public UserSubscription changeSubscriptionStatus(
            @AuthenticationPrincipal User profile,
            @PathVariable("subscriberId") User subscriber
    ) {
        return profileService.changeSubscriptionStatus(profile, subscriber);
    }
}