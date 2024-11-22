package ru.yandex.practicum.filmorate.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.GenresFilm;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenresFilmMapper implements RowMapper<GenresFilm> {
    @Override
    public GenresFilm mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = Genre.builder()
                .id(rs.getLong("genre_id"))
                .name(rs.getString("genre_name"))
                .build();
        return GenresFilm.builder()
                .filmId(rs.getLong("film_id"))
                .genre(genre)
                .build();
    }
}