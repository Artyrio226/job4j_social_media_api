package ru.job4j.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.Friendship;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.model.UsersFriendships;
import ru.job4j.socialmediaapi.repository.UsersFriendshipsRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersFriendshipsService {
    private final UsersFriendshipsRepository usersFriendshipsRepository;
    private final SubscriptionService subscriptionService;
    private final FriendshipService friendshipService;

    /**
     * Отправляет запрос на дружбу. Создаётся одна запись в таблице friendship со статусом false.
     * Создаётся одна запись в таблице users_friendships.
     * Создаётся одна запись в таблице subscription (отправитель заявки становится подписчиком).
     * @param userFrom - отправитель заявки.
     * @param userTo - получатель заявки.
     * @return - true, если в методы отработали успешно.
     */
    public boolean initUsersFriendships(User userFrom, User userTo) {
        Friendship friendship = new Friendship(false, userTo);
        UsersFriendships usersFriendships = new UsersFriendships(userFrom, friendship);
        usersFriendshipsRepository.save(usersFriendships);
        return subscriptionService.createSubscription(userFrom.getActivity(), userTo);
    }

    /**
     * Метод создаёт дружбу после подтверждения получателем заявки. Создаётся одна запись в таблице
     * friendship со статусом true. В записи, созданной при заявке меняется статус на true.
     * Создаётся одна запись в таблице users_friendships.
     * Создаётся одна строка в таблице subscription (получатель заявки становится подписчиком).
     * @param userFrom - отправитель заявки.
     * @param userTo - получатель заявки.
     * @return - true, если в методы отработали успешно.
     */
    public boolean createUsersFriendships(User userFrom, User userTo) {
        boolean result = false;
        int userFromId = userFrom.getId();
        int userToId = userTo.getId();
        Optional<Friendship> optionalFriendship = friendshipService.findByUserFromIdAndUserToId(userFromId, userToId);
        if (optionalFriendship.isPresent()
            && optionalFriendship.get().isStatus()) {
            Friendship friendship = new Friendship(true, userFrom);
            UsersFriendships usersFriendships = new UsersFriendships(userTo, friendship);
            usersFriendshipsRepository.save(usersFriendships);
            subscriptionService.createSubscription(userTo.getActivity(), userFrom);
            result = friendshipService.updateStatusByFriendshipId(optionalFriendship.get().getId()) > 0;
        }
        return result;
    }

    /**
     * Один из друзей удаляет другого из друзей, также он отписывается. Удаляются две записи из
     * таблицы friendship, две записи из таблицы users_friendships и одна запись из таблицы
     * subscription (пользователь, который удаляет из друзей - отписывается). Второй пользователь
     * при этом остаётся подписчиком.
     * @param userFrom - пользователь, который удаляет из друзей.
     * @param userTo - пользователь, которого удаляют из друзей.
     * @return - true, если в методы отработали успешно.
     */
    public boolean deleteUsersFriendships(User userFrom, User userTo) {
        int userFromId = userFrom.getId();
        int userToId = userTo.getId();
        usersFriendshipsRepository.deleteUsersFriendshipsByUserFromIdAndUserToId(userFromId, userToId);
        usersFriendshipsRepository.deleteUsersFriendshipsByUserFromIdAndUserToId(userToId, userFromId);
        return subscriptionService.deleteSubscription(userFrom.getActivity(), userTo);
    }
}
