package ru.yandex.practicum.filmorate.service.genres;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenresStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenresService implements GenresServiceInterface {

    private final GenresStorage genresStorage;

    @Override
    public List<Genre> findAllGenres() {
        return genresStorage.findAll();
    }

    @Override
    public Genre findGenreById(Long id) {
        return genresStorage.findById(id)
                .orElseThrow(() -> new RuntimeException("Жанр не найден с ID: " + id));
    }

    @Override
    public Genre addGenre(Genre genre) {
        return genresStorage.save(genre);
    }

    @Override
    public Genre updateGenre(Long id, Genre genre) {
        Genre existingGenre = findGenreById(id);
        existingGenre.setName(genre.getName()); // Предполагается, что у жанра есть поле name
        return genresStorage.save(existingGenre);
    }

    @Override
    public void deleteGenre(Long id) {
        genresStorage.delete(id);
    }
}