package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendRepository {
    Friendship addFriend(Long userId, Long friendId, boolean accept);

    List<Friendship> findAllFriends(Long userId);

    Optional<Friendship> findFriendById(Long userId, Long friendId);

    boolean delete(Long userId, Long friendId);
    Friendship updateFriendStatus(Long userId, Long friendId, boolean accept);
    List<Friendship> findMutualFriends(Long userId, Long friendId);
}