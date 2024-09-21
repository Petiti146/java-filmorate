package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film newFilm);

    void addLike(long filmId, long userId);

    List<Film> getFilms();

    Film getFilm(long filmId);

    List<Film> getMostPopularFilms(int count);

    Film updateFilm(Film updatedFilm);

    Film deleteFilm(long filmId);

    void removeLike(long filmId, long userId);
}