package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    public List<Genre> getAllGenres();

    Optional<Genre> getGenreById(Long id);
}
