package ru.job4j.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_friendships")
public class UsersFriendships {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "friendship_id")
    private Friendship friendship;

    public UsersFriendships(User user, Friendship friendship) {
        this.user = user;
        this.friendship = friendship;
    }
}
