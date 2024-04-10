package ru.job4j.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table (name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean status;

    private LocalDateTime created = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Friendship(boolean status, User user) {
        this.status = status;
        this.user = user;
    }
}
