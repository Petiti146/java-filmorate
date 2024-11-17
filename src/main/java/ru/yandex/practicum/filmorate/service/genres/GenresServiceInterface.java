package ru.yandex.practicum.filmorate.service.genres;

import ru.yandex.practicum.filmorate.models.Genre;

import java.util.List;

public interface GenresServiceInterface {

    List<Genre> findAllGenres();

    Genre findGenreById(Long id);

    Genre addGenre(Genre genre);

    Genre updateGenre(Long id, Genre genre);

    void deleteGenre(Long id);
}