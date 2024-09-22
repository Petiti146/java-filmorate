package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    public void addLike(long filmId, long userId) {
        if (inMemoryUserStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        Film film = filmStorage.getFilm(filmId);
        if (film.getLikes().contains(userId)) {
            throw new ValidationException("Пользователь уже поставил лайк этому фильму");
        }
        film.getLikes().add(userId);
        filmStorage.updateFilm(film);
    }

    public void removeLike(long filmId, long userId) {
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getMostPopularFilms(int count) {
        if (count <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Count must be a positive number");
        }
        List<Film> films = filmStorage.getFilms();
        if (films == null) {
            log.error("Films list is null");
            throw new NotFoundException("Фильмов нет");
        }
        if (films.isEmpty()) {
            log.error("Films list is empty");
            throw new NotFoundException("Фильмов нет");
        }

        int limit = count;
        limit = Math.min(limit, films.size());
        List<Film> sortedFilms = new ArrayList<>(films);
        sortedFilms.sort((f1, f2) -> f2.getLikes().size() - f1.getLikes().size());
        log.debug("Films list: {}", sortedFilms);
        return sortedFilms.subList(0, limit);
    }
}