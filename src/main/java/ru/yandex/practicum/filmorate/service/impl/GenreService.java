package ru.yandex.practicum.filmorate.service.impl;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAllGenre();

    Genre getGenreById(Long id);
}
