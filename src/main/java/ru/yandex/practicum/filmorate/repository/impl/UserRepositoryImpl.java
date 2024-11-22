package ru.yandex.practicum.filmorate.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {
    private static final String FIND_ALL_QUERY = "SELECT * FROM USERS";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM USERS WHERE user_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO USERS(login, email, name, birthday)" +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE USERS SET login = ?, email = ?, name = ?, birthday = ?" +
            " WHERE user_id = ?";

    public UserRepositoryImpl(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper, User.class);
    }


    @Override
    public List<User> getUsers() {
        List<User> users = findMany(FIND_ALL_QUERY);
        log.info("Получено {} пользователей", users.size());
        return users;
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        log.info("Запрос на получение пользователя с id: {}", userId);
        return findOne(FIND_BY_ID_QUERY, userId);
    }

    @Override
    public User userCreate(User user) {
        Long id = insert(
                INSERT_QUERY,
                user.getLogin(),
                user.getEmail(),
                user.getName(),
                Timestamp.valueOf(user.getBirthday().atStartOfDay())
        );
        user.setId(id);
        log.info("Создан пользователь с id: {}", id);
        return user;
    }


    @Override
    public User userUpdate(User user) {
        update(
                UPDATE_QUERY,
                user.getLogin(),
                user.getEmail(),
                user.getName(),
                Timestamp.valueOf(user.getBirthday().atStartOfDay()),
                user.getId()
        );
        log.info("Обновлен пользователь с id: {}", user.getId());
        return user;
    }
}