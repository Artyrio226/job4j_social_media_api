package ru.job4j.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@Tag(name = "UserController", description = "UserController management APIs")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Retrieve a User by userId",
            description = "Get a User object by specifying its userId. "
                          + "The response is User object with userId, username, email and password.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")})
    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable("userId")
                                    @NotNull
                                    @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                    Integer userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    @Operation(
            summary = "Create User",
            description = "Create a new User.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = User.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = Object.class),
                            mediaType = "application/json")})})
    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
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

    @Operation(
            summary = "Update a User",
            description = "Update a User.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Не удалось обновить")})
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody User user) {
        if (!userService.updateUser(user)) {
            throw new BadRequestException("Не удалось обновить");
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update a User",
            description = "Update a User.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")})
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@Valid @RequestBody User user) {
        userService.updateUser(user);
    }

    @Operation(
            summary = "Delete User",
            description = "Delete a User by userId.")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", description = "Не удалось удалить")})
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeById(@PathVariable
                                           @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                           int userId) {
        if (!userService.deleteById(userId)) {
            throw new BadRequestException("Не удалось удалить");
        }
        return ResponseEntity.noContent().build();
    }
}