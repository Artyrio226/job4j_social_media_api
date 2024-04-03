package ru.job4j.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Chat> chats;

    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;
}
