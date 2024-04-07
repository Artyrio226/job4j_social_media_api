package ru.job4j.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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

    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Chat> chats = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFrom")
    private Set<Friendship> friendships = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;
}
