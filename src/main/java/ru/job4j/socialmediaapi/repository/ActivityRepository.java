package ru.job4j.socialmediaapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmediaapi.model.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
}