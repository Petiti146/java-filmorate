package ru.yandex.practicum.filmorate.controllertests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JdbcTest
@AutoConfigureTestDatabase
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateUser() throws Exception {
        String userJson = "{\"email\":\"user@example.com\",\"login\":\"user\",\"name\":\"User  Name\",\"birthday\":\"2000-01-01\"}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateUser() throws Exception {
        String updateUserJson = "{\"id\":1,\"email\":\"updated@example.com\",\"login\":\"user\",\"name\":\"Updated User\",\"birthday\":\"2000-01-01\"}";

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void testUnfriending() throws Exception {
        mockMvc.perform(delete("/users/1/friends/2"))
                .andExpect(status().isOk());
    }
}