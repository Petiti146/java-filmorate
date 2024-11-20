package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;

public interface LikeRepository {

    List<Like> getLikesByFilmId(Long id);

    List<Like> getLikesByUserId(Long id);

    Like addLikeToFilm(Long filmId, Long userId);
    void deleteLike(Long filmId, Long userId);
}
