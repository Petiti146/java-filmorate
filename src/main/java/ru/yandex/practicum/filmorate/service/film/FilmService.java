package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService implements FilmServiceInterface {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public void addLike(long filmId, long userId) {
        if (userStorage.getUserById(userId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        Film film = filmStorage.getFilmById((int) filmId);
        if (film.getLikes().contains(userId)) {
            throw new ValidationException("Пользователь уже поставил лайк этому фильму");
        }
        film.getLikes().add(userId);
        filmStorage.updateFilm(film);
        filmStorage.addLike(filmId, userId);
    }

    @Override
    public void addGenreToFilm(Film film, Genre genre) {
        if (film.getGenres() == null) {
            film.setGenres(new ArrayList<>());
        }
        film.getGenres().add(genre);
    }

    @Override
    public void validateFilm(Film film) {
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            throw new IllegalArgumentException("Фильм должен иметь хотя бы один жанр.");
        }
        if (film.getMpa() == null) {
            throw new IllegalArgumentException("Фильм должен иметь рейтинг MPA.");
        }
    }

    @Override
    public void removeLike(long filmId, long userId) {
        filmStorage.removeLike(filmId, userId);
    }

    @Override
    public List<Film> getMostPopularFilms(int count) {
        if (count <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Count must be a positive number");
        }
        List<Film> films = (List<Film>) filmStorage.getAllFilms();
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

    @Override
    public Film addFilm(Film film) {
        validateFilm(film);
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        validateFilm(film);
        return filmStorage.updateFilm(film);
    }

    @Override
    public List<Film> getFilms() {
        return (List<Film>) filmStorage.getAllFilms();
    }

    @Override
    public void deleteFilm(long filmId) {
        filmStorage.deleteFilmById((int) filmId);
    }

    @Override
    public Film getFilm(long id) {
        return filmStorage.getFilmById((int) id);
    }
}