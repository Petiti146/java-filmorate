package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserServiceInterface {

    void addFriend(long userId, long friendId);

    void sendFriendRequest(long userId, long friendId);

    List<User> getAllUsers();

    void acceptFriendRequest(long userId, long friendId);

    void removeFriend(long userId, long friendId);

    List<User> getCommonFriends(long userId, long otherUserId);
}