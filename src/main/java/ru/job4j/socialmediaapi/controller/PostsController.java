package ru.job4j.socialmediaapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmediaapi.dto.UserDto;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.service.PostService;
import ru.job4j.socialmediaapi.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAll() {
        return postService.findAll();
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getListUserDto(@RequestParam List<Integer> ids) {
        return userService.findAllById(ids);
    }


}
