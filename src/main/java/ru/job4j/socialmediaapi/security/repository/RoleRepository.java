package ru.job4j.socialmediaapi.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmediaapi.security.models.ERole;
import ru.job4j.socialmediaapi.security.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
