package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Friendship;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    Optional<Friendship> findById(@Param("user_from_id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query("""
            update Friendship f set f.status = true
            where f.id = :id
            """)
    int updateStatusByFriendshipId(@Param("id") int id);

    @Query("""
            select fr from Friendship fr
            join UsersFriendships uf
            where uf.user.id = ?1
            and fr.user.id = ?2
            """)
    Optional<Friendship> findByUserFromIdAndUserToId(int userFromId, int userToId);
}
