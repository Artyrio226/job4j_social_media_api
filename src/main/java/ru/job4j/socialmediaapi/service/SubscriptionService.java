package ru.job4j.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.model.Activity;
import ru.job4j.socialmediaapi.model.Subscription;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.SubscriptionRepository;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public boolean createSubscription(Activity activity, User userTo) {
        Subscription subscription = new Subscription(activity, userTo);
        subscriptionRepository.save(subscription);
        return true;
    }

    public boolean deleteSubscription(Activity activity, User user) {
        return subscriptionRepository.deleteSubscriptionByActivityIdAndUserId(activity.getId(), user.getId()) > 0;
    }
}
