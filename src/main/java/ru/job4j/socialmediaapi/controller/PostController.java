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
import ru.job4j.socialmediaapi.dto.PostDto;
import ru.job4j.socialmediaapi.exception.BadRequestException;
import ru.job4j.socialmediaapi.exception.ResourceNotFoundException;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.service.PostService;

@Tag(name = "PostController", description = "PostController management APIs")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "Retrieve a Post by postId",
            description = "Get a Post object by specifying its postId. "
                          + "The response is Post object with postId, title, text and date of created."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Post.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Пост не найден")})
    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId")
                                    @NotNull
                                    @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                    Integer postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Пост не найден"));
    }

    @Operation(
            summary = "Create Post",
            description = "Create a new Post."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = PostDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = Object.class),
                    mediaType = "application/json")})})
    @PostMapping
    public ResponseEntity<PostDto> save(@Valid @RequestBody PostDto post) {
        postService.createPost(post);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @Operation(
            summary = "Update a Post",
            description = "Update a Post."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Не удалось обновить")})
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody PostDto post) {
        if (!postService.update(post)) {
            throw new BadRequestException("Не удалось обновить");
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update a Post",
            description = "Update a Post.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")})
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@Valid @RequestBody PostDto post) {
        postService.update(post);
    }

    @Operation(
            summary = "Delete Post",
            description = "Delete a Post by postId.")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", description = "Не удалось удалить")})
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable
                                               @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                               int postId) {
        if (!postService.deletePostById(postId)) {
            throw new BadRequestException("Не удалось удалить");
        }
        return ResponseEntity.noContent().build();
    }
}
