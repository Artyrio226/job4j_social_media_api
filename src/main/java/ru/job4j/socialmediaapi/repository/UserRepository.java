package ru.job4j.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
            select user from User user
            where user.username = :username
            and user.password = :password
            """)
    Optional<User> findByUsernameAndPassword(@Param("username") String title, @Param("password") String password);

    @Query("""
            select user from User user
            join Activity act
            join Subscription sub
            where sub.user.id = ?1
            """)
    List<User> findAllSubscribersById(int id);

    @Query("""
            select fr.user from Friendship fr
            join UsersFriendships uf
            join User user
            where user.id = ?1 and fr.status = true
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

    @Modifying
    @Query("""
        update User u
        set u.username = :#{#user.username},
        u.email = :#{#user.email},
        u.password = :#{#user.password}
        where u.id=:#{#user.id}
        """)
    int update(@Param("user") User user);

    int deleteUserById(int id);
}
