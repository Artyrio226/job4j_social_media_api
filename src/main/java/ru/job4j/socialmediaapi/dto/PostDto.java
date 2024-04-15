package ru.job4j.socialmediaapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Поле 'title' должно быть заполнено")
    private String title;

    @NotBlank(message = "Поле 'text' должно быть заполнено")
    private String text;
    private LocalDateTime created = LocalDateTime.now();
    private int user;

    @JsonIgnore
    private Set<Image> images = new HashSet<>();
}
