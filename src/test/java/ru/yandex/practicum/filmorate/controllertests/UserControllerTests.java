package ru.yandex.practicum.filmorate.controllertests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTests {

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        UserDto user1 = UserDto.builder()
                .id(1L)
                .email("user1@examplee.com")
                .login("user1")
                .name("User  One")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();

        UserDto user2 = UserDto.builder()
                .id(2L)
                .email("user2@example.com")
                .login("user2")
                .name("User  Two")
                .birthday(LocalDate.of(2001, 1, 1))
                .build();

        when(userServiceImpl.getUsers()).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> users = userController.findAll();

        assertEquals(2, users.size());
        verify(userServiceImpl, times(1)).getUsers();
    }

    @Test
    void create() {
        User user = User.builder()
                .email("newuser@example.com")
                .login("newuser")
                .name("New User")
                .birthday(LocalDate.of(2023, 1, 1))
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("newuser@example.com")
                .login("newuser")
                .name("New User")
                .birthday(LocalDate.of(2023, 1, 1))
                .build();

        when(userServiceImpl.userCreate(user)).thenReturn(userDto);

        UserDto createdUser = userController.create(user);

        assertEquals(userDto, createdUser);
        verify(userServiceImpl, times(1)).userCreate(user);
    }
}