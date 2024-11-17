package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.Film;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public Film createFilm(Film film) {
        String sql = "INSERT INTO films (name, release_date, description, duration, rate) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, film.getName());
            ps.setObject(2, film.getReleaseDate());
            ps.setString(3, film.getDescription());
            ps.setInt(4, film.getDuration());
            return ps;
        }, keyHolder);

        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    public Film updateFilm(Film film) {
        String sql = "UPDATE films SET name = ?, release_date = ?, description = ?, duration = ?, rate = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getReleaseDate(),
                film.getDescription(),
                film.getDuration(),
                film.getId());
        return film;
    }

    public Collection<Film> getAllFilms() {
        String sql = "SELECT f.*, m.id AS mpa_id, m.name AS mpa_name FROM films f LEFT JOIN mpas m ON f.mpa_id = m.id";
        return jdbcTemplate.query(sql, new FilmMapper());
    }

    public Film getFilmById(Integer id) {
        String sql = "SELECT f.*, m.id AS mpa_id, m.name AS mpa_name FROM films f LEFT JOIN mpas m ON f.mpa_id = m.id WHERE f.id = ?";
        return jdbcTemplate.queryForObject(sql, new FilmMapper(), id);
    }

    public void deleteFilmById(Integer id) {
        String sql = "DELETE FROM films WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void addLike(long filmId, long userId) {
        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    public void removeLike(long filmId, long userId) {
        String sql = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }
}
