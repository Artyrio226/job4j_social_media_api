package ru.job4j.socialmediaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.socialmediaapi.model.Post;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "UserDto Model Information")
public class UserDto {
    private Integer id;

    @Schema(description = "UserName title", example = "Mediator")
    private String username;

    @Schema(description = "List of Posts")
    private List<Post> posts;
}
