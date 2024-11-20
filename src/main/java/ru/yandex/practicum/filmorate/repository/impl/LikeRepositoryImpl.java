package ru.yandex.practicum.filmorate.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.repository.LikeRepository;

import java.util.List;

@Slf4j
@Repository
public class LikeRepositoryImpl extends BaseRepository<Like> implements LikeRepository {
    private static final String INSERT_QUERY = "INSERT INTO LIKES(film_id, user_id) VALUES (?, ?)";
    private static final String FIND_BY_FILM_ID_QUERY = "SELECT * FROM LIKES WHERE film_id = ?";
    private static final String FIND_BY_USER_ID_QUERY = "SELECT * FROM LIKES WHERE user_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM LIKES WHERE film_id = ? AND user_id = ?";

    public LikeRepositoryImpl(JdbcTemplate jdbc, RowMapper<Like> mapper) {
        super(jdbc, mapper, Like.class);
    }

    @Override
    public List<Like> getLikesByFilmId(Long id) {
        List<Like> likes = findMany(FIND_BY_FILM_ID_QUERY, id);
        log.info("Найдено {} лайков для фильма с ID: {}", likes.size(), id);
        return likes;
    }

    @Override
    public List<Like> getLikesByUserId(Long id) {
        List<Like> likes = findMany(FIND_BY_USER_ID_QUERY, id);
        log.info("Найдено {} лайков для пользователя c конкретным id: {}", likes.size(), id);
        return likes;
    }

    @Override
    public Like addLikeToFilm(Long filmId, Long userId) {
        long id = insert(INSERT_QUERY, filmId, userId); // Выполняем вставку в БД
        if (id > 0) {
            log.info("Лайк успешно добавлен для фильма с ID: {} от пользователя с ID: {}", filmId, userId);
            return Like.builder()
                    .filmId(filmId)
                    .userId(userId)
                    .build();
        }
        throw new InternalServerException("Лайк небыл добавлен");
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        log.info("Удаление лайка для фильма с конкретным id: {} от пользователя с ID: {}", filmId, userId);
        if (delete(DELETE_QUERY, filmId, userId)) {
            log.info("Лайк успешно удален для фильма конкретным id: {} от пользователя с ID: {}", filmId, userId);
        } else {
            throw new InternalServerException("Не удалось удалить лайк для фильма конкретным id: " + filmId +
                    " от пользователя с ID: " + userId);
        }
    }
}