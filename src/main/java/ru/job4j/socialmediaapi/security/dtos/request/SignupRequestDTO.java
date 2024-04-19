package ru.job4j.socialmediaapi.security.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Set<String> role;
}
