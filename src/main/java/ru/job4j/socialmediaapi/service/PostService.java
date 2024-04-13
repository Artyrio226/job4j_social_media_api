package ru.job4j.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.dto.PostDto;
import ru.job4j.socialmediaapi.model.Image;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.ImageRepository;
import ru.job4j.socialmediaapi.repository.PostRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ImageRepository imageRepository;

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public Optional<Post> createPost(int userId, String title, String text, Set<Image> images) {
        Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        post.getImages().addAll(images);
        Optional<User> byId = userService.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            post.setUser(user);
            user.getPosts().add(post);
        }
        return Optional.of(postRepository.save(post));
    }

    public boolean updatePost(User user, Post post) {
        boolean result = false;
        Optional<Post> optionalPost = postRepository.findById(post.getId());
        if (optionalPost.isPresent()
            && Objects.equals(user.getId(), optionalPost.get().getUser().getId())) {
            postRepository.save(post);
            result = true;
        }
        return result;
    }

    public boolean deletePost(User user, Post post) {
        boolean result = false;
        int id = post.getId();
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()
            && Objects.equals(user.getId(), optionalPost.get().getUser().getId())) {
            result = postRepository.deleteByPostId(id) > 0;
        }
        return result;
    }

    public boolean update(PostDto post) {
        boolean rsl = false;
        Optional<Post> optionalPost = findById(post.getId());
        if (optionalPost.isPresent()) {
            Post rslPost = optionalPost.get();
            rslPost.setTitle(post.getTitle());
            rslPost.setText(post.getText());
            imageRepository.deleteAll(optionalPost.get().getImages());
            imageRepository.saveAll(post.getImages());
            rsl = postRepository.updatePostById(rslPost) > 0;
        }
        return rsl;
    }

    public boolean deletePostById(int id) {
        return postRepository.deletePostById(id) > 0;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
