package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film newFilm);

    List<Film> getFilms();

    Film getFilm(long filmId);

    Film updateFilm(Film updatedFilm);

    void deleteFilm(long filmId);

}