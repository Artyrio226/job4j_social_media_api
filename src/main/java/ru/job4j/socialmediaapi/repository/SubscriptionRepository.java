package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    int deleteSubscriptionByActivityIdAndUserId(@Param("activity_id") int activityId, @Param("user_id") int userId);
}
