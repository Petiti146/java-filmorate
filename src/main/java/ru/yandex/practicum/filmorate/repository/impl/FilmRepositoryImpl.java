package ru.yandex.practicum.filmorate.repository.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FilmRepositoryImpl extends BaseRepository<Film> implements FilmRepository {
    private static final String FIND_ALL_QUERY = "SELECT * FROM FILMS f JOIN MPA_TYPE mp ON f.mpa = mp.MPA_ID";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM FILMS f JOIN MPA_TYPE mp ON f.mpa = mp.MPA_ID " +
            "WHERE film_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO FILMS(film_name, description, release_date, duration, " +
            "mpa) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE FILMS SET film_name = ?, description = ?, release_date = ?, " +
            "duration = ? WHERE film_id = ?";
    private static final String FIND_POPULAR_LIMIT_QUERY = "SELECT f.*, mp.MPA_NAME FROM FILMS f " +
            "LEFT JOIN LIKES l ON f.FILM_ID = l.FILM_ID " +
            "JOIN MPA_TYPE mp ON f.mpa = mp.MPA_ID " +
            "GROUP BY f.FILM_ID " +
            "ORDER BY COUNT(l.USER_ID) DESC " +
            "LIMIT ?;";

    public FilmRepositoryImpl(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    @Override
    public List<Film> getFilms() {
        log.info("Запрос на получение всех фильмов.");
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Film> getFilmById(Long filmId) {
        log.info("Запрос на получение фильма с id: {}", filmId);
        return findOne(FIND_BY_ID_QUERY, filmId);
    }

    @Override
    public Film createFilm(Film film) {
        Long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Timestamp.valueOf(film.getReleaseDate().atStartOfDay()),
                film.getDuration(),
                film.getMpa().getId()
        );
        film.setId(id);
        log.info("Создан фильм с id: {}", id);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("Обновление фильма с id: {}", film.getId());
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                Timestamp.valueOf(film.getReleaseDate().atStartOfDay()),
                film.getDuration(),
                film.getId()
        );
        log.info("Фильм с id: {} успешно обновлён.", film.getId());
        return film;
    }

    @Override
    public List<Film> getMostPopularByNumberOfLikes(Long count) {
        log.info("Запрос на получение {} самых популярных фильмов.", count);
        return findMany(FIND_POPULAR_LIMIT_QUERY, count);
    }
}