package ru.yandex.practicum.filmorate.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.GenresFilm;
import ru.yandex.practicum.filmorate.repository.GenresFilmRepository;

import java.util.List;

@Slf4j
@Repository
public class GenresFilmRepositoryImpl extends BaseRepository<GenresFilm> implements GenresFilmRepository {
    private static final String INSERT_QUERY = "INSERT INTO GENRES_FILM(film_id, genre_id) VALUES (?, ?)";
    private static final String FIND_BY_FILM_ID_QUERY = "SELECT * FROM GENRES_FILM gf JOIN GENRE_TYPE gt " +
            "ON gf.GENRE_ID = gt.GENRE_ID WHERE film_id = ?";

    public GenresFilmRepositoryImpl(JdbcTemplate jdbc, RowMapper<GenresFilm> mapper) {
        super(jdbc, mapper, GenresFilm.class);
    }

    @Override
    public List<GenresFilm> getGenresByFilmId(Long id) {
        List<GenresFilm> genres = findMany(FIND_BY_FILM_ID_QUERY, id);
        log.info("Найдено {} жанров для фильма с id: {}", genres.size(), id);
        return genres;
    }

    @Override
    public void addGenreToFilm(Long filmId, Long genreId) {
        if (insert(INSERT_QUERY, filmId, genreId) > 0) {
            log.info("Жанр с id: {} успешно добавлен к фильму с id: {}", genreId, filmId);
        } else {
            throw new InternalServerException("Не удалось добавить жанр с id: " + genreId +
                    " к фильму с id: " + filmId);
        }
    }
}