package ru.yandex.practicum.filmorate.controllertests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.impl.FilmServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FilmControllerTests {

    @Mock
    private FilmServiceImpl filmService;

    @InjectMocks
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        FilmDto film1 = FilmDto.builder()
                .id(1L)
                .name("Film 1")
                .description("Description 1")
                .releaseDate(LocalDate.of(2021, 1, 1))
                .duration(120)
                .build();

        FilmDto film2 = FilmDto.builder()
                .id(2L)
                .name("Film 2")
                .description("Description 2")
                .releaseDate(LocalDate.of(2022, 1, 1))
                .duration(150)
                .build();

        when(filmService.getFilms()).thenReturn(Arrays.asList(film1, film2));

        List<FilmDto> films = filmController.findAll();

        assertEquals(2, films.size());
        verify(filmService, times(1)).getFilms();
    }

    @Test
    void create() {
        Film film = Film.builder()
                .name("New Film")
                .description("New Description")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .duration(90)
                .build();

        FilmDto filmDto = FilmDto.builder()
                .id(1L)
                .name("New Film")
                .description("New Description")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .duration(90)
                .build();

        when(filmService.createFilm(film)).thenReturn(filmDto);

        FilmDto createdFilm = filmController.create(film);

        assertEquals(filmDto, createdFilm);
        verify(filmService, times(1)).createFilm(film);
    }

    @Test
    void update() {
        UpdateFilmDto updateFilmDto = new UpdateFilmDto();
        updateFilmDto.setId(1L);
        updateFilmDto.setName("Updated Film");
        updateFilmDto.setDescription("Updated Description");
        updateFilmDto.setReleaseDate(LocalDate.of(2023, 1, 1));
        updateFilmDto.setDuration(100);

        FilmDto filmDto = FilmDto.builder()
                .id(1L)
                .name("Updated Film")
                .description("Updated Description")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .duration(100)
                .build();

        when(filmService.updateFilm(updateFilmDto)).thenReturn(filmDto);

        FilmDto updatedFilm = filmController.update(updateFilmDto);

        assertEquals(filmDto, updatedFilm);
        verify(filmService, times(1)).updateFilm(updateFilmDto);
    }

    @Test
    void deleteLike() {
        FilmDto filmDto = FilmDto.builder()
                .id(1L)
                .name("Film 1")
                .description("Description 1")
                .releaseDate(LocalDate.of(2021, 1, 1))
                .duration(120)
                .build();

        when(filmService.deleteLike(1L, 2L)).thenReturn(filmDto);

        FilmDto result = filmController.deleteLike(1L, 2L);

        assertEquals(filmDto, result);
        verify(filmService, times(1)).deleteLike(1L, 2L);
    }
}