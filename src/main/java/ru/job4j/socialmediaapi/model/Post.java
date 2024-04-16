package ru.job4j.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "posts")
@Schema(description = "Post Model Information")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Поле 'title' должно быть заполнено")
    @Schema(description = "Title of the Post", example = "My first post")
    private String title;

    @NotBlank(message = "Поле 'text' должно быть заполнено")
    @Schema(description = "Text of the Post", example = "Text of my post")
    private String text;

    @Schema(description = "Date of creation", example = "2023-10-15T15:15:15")
    private LocalDateTime created = LocalDateTime.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private Set<Image> images = new HashSet<>();
}
