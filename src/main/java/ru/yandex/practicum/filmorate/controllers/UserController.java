package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.UpdateUserDto;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public List<UserDto> findAll() {
        return userServiceImpl.getUsers();
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto user) {
        return userServiceImpl.userCreate(user);
    }

    @PutMapping
    public UserDto update(@RequestBody UpdateUserDto request) {
        return userServiceImpl.userUpdate(request);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userServiceImpl.getUserById(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public UserDto unfriending(@PathVariable Long id, @PathVariable Long friendId) {
        return userServiceImpl.unfriending(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public UserDto friending(@PathVariable Long id, @PathVariable Long friendId) {
        return userServiceImpl.friending(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserDto> listMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userServiceImpl.listOfMutualFriends(id, otherId);
    }

    @GetMapping("/{id}/friends")
    public List<UserDto> getUserFriends(@PathVariable Long id) {
        return userServiceImpl.getUserFriends(id);
    }

}