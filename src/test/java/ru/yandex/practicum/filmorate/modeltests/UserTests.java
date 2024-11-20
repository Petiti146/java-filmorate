package ru.yandex.practicum.filmorate.modeltests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTests {

    @Test
    void testUserBuilder() {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .login("user")
                .name("User Name")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("user", user.getLogin());
        assertEquals("User Name", user.getName());
        assertEquals(LocalDate.of(2000, 1, 1), user.getBirthday());
    }
}