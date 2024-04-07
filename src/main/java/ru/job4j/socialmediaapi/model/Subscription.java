package ru.job4j.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Activity activity;

    @ManyToOne
    private User user;

    private LocalDateTime created = LocalDateTime.now();

    public Subscription(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }
}
