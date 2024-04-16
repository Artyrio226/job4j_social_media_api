package ru.job4j.socialmediaapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Title of the Post", example = "My first post")
    private String title;

    @NotBlank(message = "Поле 'text' должно быть заполнено")
    @Schema(description = "Text of the Post", example = "Text of my post")
    private String text;

    @Schema(description = "Date of creation", example = "2023-10-15T15:15:15")
    private LocalDateTime created = LocalDateTime.now();

    @Schema(description = "User's id who created the Post", example = "3")
    private int user;

    @JsonIgnore
    private Set<Image> images = new HashSet<>();
}
