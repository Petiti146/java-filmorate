package ru.yandex.practicum.filmorate.modeltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FilmTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testInsertFilm() {
        String sql = "INSERT INTO films (film_id, film_name, description, release_date, duration, mpa) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 1L, "Test Film", "Test Description", "2023-01-01", 120, 1L);

        String query = "SELECT film_name FROM films WHERE film_id = ?";
        String filmName = jdbcTemplate.queryForObject(query, new Object[]{1L}, String.class);

        assertThat(filmName).isEqualTo("Test Film");
    }

    @Test
    void testFindAllFilms() {
        String sql = "INSERT INTO films (film_id, film_name, description, release_date, duration, mpa) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 1L, "Film 1", "Description 1", "2023-01-01", 100, 1L);
        jdbcTemplate.update(sql, 2L, "Film 2", "Description 2", "2023-01-02", 120, 1L);

        String query = "SELECT COUNT(*) FROM films";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void testUpdateFilm() {
        String sql = "INSERT INTO films (film_id, film_name, description, release_date, duration, mpa) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 1L, "Old Film", "Old Description", "2023-01-01", 120, 1L);

        String updateSql = "UPDATE films SET film_name = ?, description = ? WHERE film_id = ?";
        jdbcTemplate.update(updateSql, "Updated Film", "Updated Description", 1L);

        String query = "SELECT film_name FROM films WHERE film_id = ?";
        String filmName = jdbcTemplate.queryForObject(query, new Object[]{1L}, String.class);

        assertThat(filmName).isEqualTo("Updated Film");
    }

    @Test
    void testDeleteFilm() {
        String sql = "INSERT INTO films (film_id, film_name, description, release_date, duration, mpa) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 1L, "Film to Delete", "Description", "2023-01-01", 120, 1L);

        String deleteSql = "DELETE FROM films WHERE film_id = ?";
        jdbcTemplate.update(deleteSql, 1L);

        String query = "SELECT COUNT(*) FROM films WHERE film_id = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{1L}, Integer.class);

        assertThat(count).isEqualTo(0);
    }
}