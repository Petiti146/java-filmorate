package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {

    List<Film> getFilms();

    Optional<Film> getFilmById(Long filmId);

    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getMostPopularByNumberOfLikes(Long count);
}