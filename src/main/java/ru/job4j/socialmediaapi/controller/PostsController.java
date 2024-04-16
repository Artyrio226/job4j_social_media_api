package ru.job4j.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmediaapi.dto.UserDto;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.service.PostService;
import ru.job4j.socialmediaapi.service.UserService;

import java.util.List;

@Tag(name = "PostsController", description = "PostsController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;
    private final UserService userService;

    @Operation(
            summary = "Get all Posts",
            description = "Retrieve list of all Posts.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)),
                            mediaType = "application/json")})})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAll() {
        return postService.findAll();
    }

    @Operation(
            summary = "Get User Posts",
            description = "Retrieve list of Users containing their Posts by user ids.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
                            mediaType = "application/json")})})
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getListUserDto(@RequestParam List<Integer> ids) {
        return userService.findAllById(ids);
    }


}
