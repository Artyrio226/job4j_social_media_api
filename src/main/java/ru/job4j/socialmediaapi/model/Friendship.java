package ru.job4j.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userFrom;

    @ManyToOne
    private User userTo;

    public Friendship(boolean status, User userFrom, User userTo) {
        this.status = status;
        this.userFrom = userFrom;
        this.userTo = userTo;
    }
}
