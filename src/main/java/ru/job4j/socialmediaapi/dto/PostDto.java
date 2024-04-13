package ru.job4j.socialmediaapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.job4j.socialmediaapi.model.Image;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Integer id;
    private String title;
    private String text;
    private LocalDateTime created = LocalDateTime.now();
    private int user;

    @JsonIgnore
    private Set<Image> images = new HashSet<>();
}
