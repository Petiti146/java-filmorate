package ru.yandex.practicum.filmorate.service.impl;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmDto;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    List<FilmDto> getFilms();

    FilmDto createFilm(Film film);

    FilmDto updateFilm(UpdateFilmDto request);

    FilmDto addLike(Long filmId, Long userId);

    FilmDto deleteLike(Long filmId, Long userId);

    List<FilmDto> getMostPopularByNumberOfLikes(Long count);

    FilmDto getWithGenre(Long id);
}
