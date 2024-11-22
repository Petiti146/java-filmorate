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
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmDto;
import ru.yandex.practicum.filmorate.service.impl.FilmServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FilmControllerTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockMvc mockMvc;
    private FilmServiceImpl filmService;

    @BeforeEach
    void setup() {
        filmService = Mockito.mock(FilmServiceImpl.class);
        FilmController filmController = new FilmController(filmService);
        mockMvc = MockMvcBuilders.standaloneSetup(filmController).build();

        // Создание таблиц
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS films (" +
                "film_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "film_name VARCHAR NOT NULL," +
                "description VARCHAR," +
                "release_date TIMESTAMP," +
                "duration INTEGER," +
                "mpa INTEGER REFERENCES mpa_type(mpa_id))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mpa_type (" +
                "mpa_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "mpa_name VARCHAR UNIQUE)");
    }

    @Test
    void testCreateFilm() throws Exception {
        FilmDto filmDto = FilmDto.builder()
                .name("Inception")
                .description("A mind-bending thriller")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(null) // Замените на реальный объект Mpa, если нужно
                .build();

        when(filmService.createFilm(any())).thenReturn(filmDto);

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Inception\",\"description\":\"A mind-bending thriller\",\"releaseDate\":\"2010-07-16\",\"duration\":148}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Inception"));
    }

    @Test
    void testGetFilmById() throws Exception {
        FilmDto filmDto = FilmDto.builder()
                .id(1L)
                .name("Inception")
                .description("A mind-bending thriller")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(null) // Замените на реальный объект Mpa, если нужно
                .build();

        when(filmService.getWithGenre(1L)).thenReturn(filmDto);

        mockMvc.perform(get("/films/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Inception"));
    }

    @Test
    void testUpdateFilm() throws Exception {
        UpdateFilmDto updateFilmDto = new UpdateFilmDto();
        updateFilmDto.setId(1L);
        updateFilmDto.setName("Inception Updated");
        updateFilmDto.setDescription("A thrilling movie");
        updateFilmDto.setReleaseDate(LocalDate.of(2010, 7, 16));
        updateFilmDto.setDuration(150);

        FilmDto filmDto = FilmDto.builder()
                .id(1L)
                .name("Inception Updated")
                .description("A thrilling movie")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(150)
                .mpa(null) // Замените на реальный объект Mpa, если нужно
                .build();

        when(filmService.updateFilm(any())).thenReturn(filmDto);

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Inception Updated\",\"description\":\"A thrilling movie\",\"releaseDate\":\"2010-07-16\",\"duration\":150}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Inception Updated"));
    }

    @Test
    void testGetAllFilms() throws Exception {
        FilmDto filmDto1 = FilmDto.builder()
                .id(1L)
                .name("Inception")
                .description("A mind-bending thriller")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(null) // Замените на реальный объект Mpa, если нужно
                .build();

        FilmDto filmDto2 = FilmDto.builder()
                .id(2L)
                .name("Interstellar")
                .description("A journey through space and time")
                .releaseDate(LocalDate.of(2014, 11, 7))
                .duration(169)
                .mpa(null) // Замените на реальный объект Mpa, если нужно
                .build();

        when(filmService.getFilms()).thenReturn(List.of(filmDto1, filmDto2));

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Inception"))
                .andExpect(jsonPath("$[1].name").value("Interstellar"));
    }
}