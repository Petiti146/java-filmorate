package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> getAllGenre() {
        return genreRepository.getAllGenres();
    }

    public Genre getGenreById(Long id) {
        return genreRepository.getGenreById(id)
                .orElseThrow(() -> new NotFoundException("Жанр с таким id: " + id + " не найден"));
    }
}