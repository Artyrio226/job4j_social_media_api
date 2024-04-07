package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
