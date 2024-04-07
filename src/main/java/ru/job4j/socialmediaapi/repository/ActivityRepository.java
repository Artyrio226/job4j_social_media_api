package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}
