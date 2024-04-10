package ru.job4j.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> createUser(User user) {
        return Optional.of(userRepository.save(user));
    }

    public boolean updateUser(User user) {
        return userRepository.update(user) > 0L;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public boolean deleteById(int id) {
        return userRepository.deleteUserById(id) > 0L;
    }
}
