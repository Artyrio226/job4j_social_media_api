package ru.job4j.socialmediaapi.controller;

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
import ru.job4j.socialmediaapi.service.PostService;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId")
                                    @NotNull
                                    @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                    Integer postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Пост не найден"));
    }

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

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody PostDto post) {
        if (!postService.update(post)) {
            throw new BadRequestException("Не удалось обновить");
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@Valid @RequestBody PostDto post) {
        postService.update(post);
    }

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
