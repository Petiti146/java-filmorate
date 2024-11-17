package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate()
        );
    }
}