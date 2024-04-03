package ru.job4j.socialmediaapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmediaapi.model.Chat;

public interface ChatRepository extends CrudRepository<Chat, Integer> {
}
