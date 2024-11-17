package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.Mpa;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcMpaStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> findAll() {
        String sql = "SELECT * FROM mpas";
        return jdbcTemplate.query(sql, new MpaMapper());
    }

    @Override
    public Optional<Mpa> findById(Long id) {
        String sql = "SELECT * FROM mpas WHERE id = ?";
        return jdbcTemplate.query(sql, new MpaMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Mpa save(Mpa mpa) {
        String sql = "INSERT INTO mpas (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, mpa.getName());
            return ps;
        }, keyHolder);

        mpa.setId(keyHolder.getKey().intValue());
        return mpa;
    }
}