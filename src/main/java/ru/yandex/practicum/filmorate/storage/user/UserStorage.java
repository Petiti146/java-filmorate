package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserStorage {
    User addUser(User newUser);

    List<User> getUsers();

    User getUser(long userId);

    public List<User> getFriends(long userId);

    User updateUser(User updatedUser);

    User deleteUser(long userId);
}
