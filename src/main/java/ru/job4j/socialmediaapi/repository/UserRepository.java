package ru.job4j.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
            select user from User user
            where user.name = :name
            and user.password = :password
            """)
    Optional<User> findByNameAndPassword(@Param("name") String title, @Param("password") String password);

    @Query("""
            select user from User user
            join Activity act
            join Subscription sub
            where sub.user.id = ?1
            """)
    List<User> findAllSubscribersById(int id);

    @Query("""
            select user from User user
            join Friendship fr
            where (fr.userFrom.id = ?1 or fr.userTo.id = ?1) and fr.status = true
            """)
    List<User> findAllFriendsById(int id);

    @Query("""
            select p from Post p
            join User u
            join Subscription sub
            join Activity act
            where act.user.id = ?1
            order by p.created desc
            """)
    Page<User> findAllSubscriberPostsByOrderDescWithPagination(int id, Pageable pageable);
}
