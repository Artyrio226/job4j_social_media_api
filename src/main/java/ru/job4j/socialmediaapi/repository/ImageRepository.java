package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
