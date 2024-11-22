package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.GenresFilm;

import java.util.List;

public interface GenresFilmRepository {
    List<GenresFilm> getGenresByFilmId(Long id);

    void addGenreToFilm(Long filmId, Long genreId);
}
