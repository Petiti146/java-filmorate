package ru.yandex.practicum.filmorate.service.impl;

import ru.yandex.practicum.filmorate.dto.UpdateUserDto;
import ru.yandex.practicum.filmorate.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto userUpdate(UpdateUserDto request);

    UserDto friending(Long userId, Long friendId);

    UserDto unfriending(Long userId, Long friendId);

    List<UserDto> getUserFriends(Long userId);

    List<UserDto> listOfMutualFriends(Long userId, Long friendId);
}
