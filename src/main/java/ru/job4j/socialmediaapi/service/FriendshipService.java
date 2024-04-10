package ru.job4j.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.Friendship;
import ru.job4j.socialmediaapi.repository.FriendshipRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public Optional<Friendship> findByUserFromIdAndUserToId(int userFromId, int userToId) {
        return friendshipRepository.findByUserFromIdAndUserToId(userFromId, userToId);
    }

    public int updateStatusByFriendshipId(Integer id) {
        return friendshipRepository.updateStatusByFriendshipId(id);
    }
}