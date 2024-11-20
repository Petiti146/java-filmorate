package ru.yandex.practicum.filmorate.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class GenreRepositoryImpl extends BaseRepository<Genre> implements GenreRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM GENRE_TYPE";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM GENRE_TYPE WHERE GENRE_ID = ?";

    public GenreRepositoryImpl(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper, Genre.class);
    }

    public List<Genre> getAllGenres() {
        List<Genre> genres = findMany(FIND_ALL_QUERY);
        log.info("Найдено {} жанров", genres.size());
        return genres;
    }

    public Optional<Genre> getGenreById(Long id) {
        log.info("Запрос жанра с ID: {}", id);
        return findOne(FIND_BY_ID_QUERY, id);
    }
}