package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserById(Long userId);

    List<User> getUsers();

    User userCreate(User user);

    User userUpdate(User user);

}
