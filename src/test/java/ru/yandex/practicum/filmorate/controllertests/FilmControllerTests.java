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
class FilmControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateFilm() throws Exception {
        String filmJson = "{\"name\":\"Inception\",\"description\":\"A mind-bending thriller\",\"releaseDate\":\"2010-07-16\",\"duration\":148}";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Inception"));
    }

    @Test
    void testGetFilmById() throws Exception {
        mockMvc.perform(get("/films/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateFilm() throws Exception {
        String updateFilmJson = "{\"id\":1,\"name\":\"Inception Updated\",\"description\":\"Updated description\",\"releaseDate\":\"2010-07-16\",\"duration\":148}";

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateFilmJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Inception Updated"));
    }

    @Test
    void testDeleteLike() throws Exception {
        mockMvc.perform(delete("/films/1/like/1"))
                .andExpect(status().isOk());
    }
}