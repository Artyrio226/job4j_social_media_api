package ru.job4j.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.Friendship;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.FriendshipRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final SubscriptionService subscriptionService;

    /**
     * Отправляет запрос на дружбу. Создаётся одна запись в таблице friendship со статусом false.
     * Создаётся одна строка в таблице subscription (отправитель заявки становится подписчиком).
     * @param userFrom - отправитель заявки.
     * @param userTo - получатель заявки.
     * @return - true, если в методы отработали успешно.
     */
    public boolean initFriendship(User userFrom, User userTo) {
        Friendship friendship = new Friendship(false, userFrom, userTo);
        friendshipRepository.save(friendship);
        return subscriptionService.createSubscription(userFrom.getActivity(), userTo);
    }

    /**
     * Метод создаёт дружбу после подтверждения получателем заявки. Создаётся одна запись в таблице
     * friendship со статусом true. В записи, созданной при заявке меняется статус на true.
     * Создаётся одна строка в таблице subscription (получатель заявки становится подписчиком).
     * @param userFrom - отправитель заявки.
     * @param userTo - получатель заявки.
     * @return - true, если в методы отработали успешно.
     */
    public boolean createFriendship(User userFrom, User userTo) {
        boolean result = false;
        int userFromId = userFrom.getId();
        Optional<Friendship> optionalFriendship = friendshipRepository.findById(userFromId);
        if (optionalFriendship.isPresent()
            && optionalFriendship.get().isStatus()) {
            Friendship friendship = new Friendship(true, userTo, userFrom);
            friendshipRepository.save(friendship);
            subscriptionService.createSubscription(userTo.getActivity(), userFrom);
            result = friendshipRepository.updateStatusByFriendshipId(optionalFriendship.get().getId()) > 0;
        }
        return result;
    }

    /**
     * Один из друзей удаляет другого из друзей, также он отписывается. Удаляются две записи из
     * таблицы friendship и одна запись из таблицы subscription (пользователь, который удаляет из
     * друзей - отписывается). Второй пользователь при этом остаётся подписчиком.
     * @param userFrom - пользователь, который удаляет из друзей.
     * @param userTo - пользователь, которого удаляют из друзей.
     * @return - true, если в методы отработали успешно.
     */
    public boolean deleteFriendship(User userFrom, User userTo) {
        int userFromId = userFrom.getId();
        int userToId = userTo.getId();
        friendshipRepository.deleteFriendshipByUserFromId(userFromId);
        friendshipRepository.deleteFriendshipByUserFromId(userToId);
        return subscriptionService.deleteSubscription(userFrom.getActivity(), userTo);
    }
}
