package ru.job4j.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserId(Integer id);

    List<Post> findByCreatedGreaterThanEqualAndCreatedLessThanEqual(LocalDateTime startAt, LocalDateTime finishAt);

    Page<Post> findByOrderByCreated(LocalDateTime startAt, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            update Post p set p.title = :title, p.text = :text
            where p.id = :id
            """)
    int updateTitleAndText(@Param("title") String title, @Param("text") String text, @Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query("""
            delete Image i where i.post.id  = :id
            """)
    int deleteImageByPostId(@Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query("""
            delete Post p where p.id  = :id
            """)
    int deleteByPostId(@Param("id") Integer id);

    @Modifying
    @Query("""
        update Post p
        set p.title = :#{#post.title},
        p.text = :#{#post.text}
        where p.id=:#{#post.id}
        """)
    int updatePostById(@Param("post")Post post);

    int deletePostById(int id);
}
