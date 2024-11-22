package ru.yandex.practicum.filmorate.controllertests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.service.impl.UserServiceImpl;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserControllerTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockMvc mockMvc;
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        userService = Mockito.mock(UserServiceImpl.class);
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Создание таблиц
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "email VARCHAR NOT NULL UNIQUE," +
                "login VARCHAR NOT NULL," +
                "name VARCHAR," +
                "birthday DATE NOT NULL)");
    }

    @Test
    void testCreateUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .email("test@example.com")
                .login("testuser")
                .name("Test User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        when(userService.userCreate(any())).thenReturn(userDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"login\":\"testuser\",\"name\":\"Test User\",\"birthday\":\"1990-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("testuser"));
    }

    @Test
    void testGetUserById() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("test@example.com")
                .login("testuser")
                .name("Test User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        when(userService.getUserById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("testuser"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("test@example.com")
                .login("testuser")
                .name("Updated User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        when(userService.userUpdate(any())).thenReturn(userDto);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"email\":\"test@example.com\",\"login\":\"testuser\",\"name\":\"Updated User\",\"birthday\":\"1990-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));
    }
}