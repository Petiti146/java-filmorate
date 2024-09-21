package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserStorage {
    User addUser(User newUser);

    void addFriend(long userId, long friendId);

    List<User> getUsers();

    User getUser(long userId);

    int getCommonFriends(long userId, long otherUserId);

    public List<User> getFriends(long userId);

    User updateUser(User updatedUser);

    User deleteUser(long userId);

    void removeFriend(long userId, long friendId);
}
