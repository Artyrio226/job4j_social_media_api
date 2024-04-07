package ru.job4j.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.Image;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.PostRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Optional<Post> createPost(User user, String title, String text, Image image) {
        Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        post.getImages().add(image);
        image.setPost(post);
        post.setUser(user);
        user.getPosts().add(post);
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
}
