package ru.yandex.practicum.filmorate.storage.genres;

import ru.yandex.practicum.filmorate.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenresStorage {
    List<Genre> findAll();
    Optional<Genre> findById(Long id);
    Genre save(Genre genre);
    void delete(Long id);
}