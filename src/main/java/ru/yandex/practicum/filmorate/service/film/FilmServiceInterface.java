package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;

import java.util.List;

public interface FilmServiceInterface {

    void addLike(long filmId, long userId);

    void addGenreToFilm(Film film, Genre genre);

    void validateFilm(Film film);

    void removeLike(long filmId, long userId);

    List<Film> getMostPopularFilms(int count);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    void deleteFilm(long filmId);

    Film getFilm(long id);
}