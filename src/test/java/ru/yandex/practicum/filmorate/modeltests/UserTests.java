package ru.yandex.practicum.filmorate.modeltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testInsertUser() {
        String sql = "INSERT INTO users (id, email, login, name, birthday) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 1L, "user@example.com", "userLogin", "User  Name", "1990-01-01");

        String query = "SELECT name FROM users WHERE id = ?";
        String name = jdbcTemplate.queryForObject(query, new Object[]{1L}, String.class);

        assertThat(name).isEqualTo("User  Name");
    }

    @Test
    void testFindAllUsers() {
        String sql = "INSERT INTO users (id, email, login, name, birthday) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 1L, "user1@example.com", "userLogin1", "User  1", "1990-01-01");
        jdbcTemplate.update(sql, 2L, "user2@example.com", "userLogin2", "User  2", "1991-01-01");

        String query = "SELECT COUNT(*) FROM users";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void testAddFriend() {
        String sql = "INSERT INTO users (id, email, login, name, birthday) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 1L, "user1@example.com", "userLogin1", "User  1", "1990-01-01");
        jdbcTemplate.update(sql, 2L, "user2@example.com", "userLogin2", "User  2", "1991-01-01");

        String addFriendSql = "INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(addFriendSql, 1L, 2L);

        String query = "SELECT COUNT(*) FROM user_friends WHERE user_id = ?";
        Integer friendCount = jdbcTemplate.queryForObject(query, new Object[]{1L}, Integer.class);

        assertThat(friendCount).isEqualTo(1);
    }
}