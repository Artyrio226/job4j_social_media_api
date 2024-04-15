package ru.job4j.socialmediaapi.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmediaapi.exception.BadRequestException;
import ru.job4j.socialmediaapi.exception.ResourceNotFoundException;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.service.UserService;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable("userId")
                                    @NotNull
                                    @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                    Integer userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        userService.createUser(user);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(user);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody User user) {
        if (!userService.updateUser(user)) {
            throw new BadRequestException("Не удалось обновить");
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeById(@PathVariable int userId) {
        if (!userService.deleteById(userId)) {
            throw new BadRequestException("Не удалось удалить");
        }
        return ResponseEntity.noContent().build();
    }
}