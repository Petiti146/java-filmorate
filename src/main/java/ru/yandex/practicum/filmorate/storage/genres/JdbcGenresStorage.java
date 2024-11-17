package ru.yandex.practicum.filmorate.storage.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.Genre;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcGenresStorage implements GenresStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM genres";
        return jdbcTemplate.query(sql, new GenreMapper());
    }

    @Override
    public Optional<Genre> findById(Long id) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        return jdbcTemplate.query(sql, new GenreMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            String sql = "INSERT INTO genres (name) VALUES (?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, genre.getName());
                return ps;
            }, keyHolder);
            genre.setId(keyHolder.getKey().intValue());
        } else {
            String sql = "UPDATE genres SET name = ? WHERE id = ?";
            jdbcTemplate.update(sql, genre.getName(), genre.getId());
        }
        return genre;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM genres WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}