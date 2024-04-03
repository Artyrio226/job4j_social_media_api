package ru.job4j.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmediaapi.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findByUserId(Integer id);

    List<Post> findByCreatedGreaterThanEqualAndCreatedLessThanEqual(LocalDateTime startAt, LocalDateTime finishAt);

    Page<Post> findByOrderByCreated(LocalDateTime startAt, Pageable pageable);
}
