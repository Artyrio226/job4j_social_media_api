package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.socialmediaapi.model.UsersFriendships;

public interface UsersFriendshipsRepository extends JpaRepository<UsersFriendships, Integer> {

    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE FROM users_friendships uf
            USING friendship fr
            where uf.user_id = ?1
            and  fr.user_id = ?2
            """, nativeQuery = true)
    int deleteUsersFriendshipsByUserFromIdAndUserToId(int userId, int user);
}
