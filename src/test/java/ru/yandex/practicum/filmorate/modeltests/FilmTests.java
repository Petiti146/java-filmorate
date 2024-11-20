package ru.yandex.practicum.filmorate.modeltests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilmTests {

    @Test
    void testFilmBuilder() {
        Film film = Film.builder()
                .id(1L)
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(120)
                .build();

        assertNotNull(film);
        assertEquals(1L, film.getId());
        assertEquals("Film Name", film.getName());
        assertEquals("Film Description", film.getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), film.getReleaseDate());
        assertEquals(120, film.getDuration());
    }

    @Test
    void testFilmGetters() {
        Film film = Film.builder()
                .id(1L)
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(120)
                .build();

        assertEquals(1L, film.getId());
        assertEquals("Film Name", film.getName());
        assertEquals("Film Description", film.getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), film.getReleaseDate());
        assertEquals(120, film.getDuration());
    }
}