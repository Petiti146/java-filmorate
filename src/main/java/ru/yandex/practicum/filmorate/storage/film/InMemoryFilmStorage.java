package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Long, Film> films = new HashMap<>();
    private long idCounter = 1;
    private final InMemoryUserStorage inMemoryUserStorage;

    @Override
    public Film addFilm(Film newFilm) {
        if (newFilm == null) {
            throw new ValidationException("Фильм не может быть null");
        }
        if (newFilm.getName() == null || newFilm.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (newFilm.getDescription() == null || newFilm.getDescription().length() > 200) {
            throw new ValidationException("Название фильма не может содержать больше 200 символов или быть пустым");
        }
        if (newFilm.getReleaseDate() == null || LocalDate.of(1895, 12, 28).isAfter(newFilm.getReleaseDate())) {
            throw new ValidationException("Дата релиза фильма не может быть раньше 1895 года или быть пустой");
        }
        if (newFilm.getDuration() <= 0) {
            throw new ValidationException("Продолжительность не может быть отрицательной или равняться нулю");
        }

        long id = getNextId();
        newFilm.setId(id);
        log.info("Adding new film: {}", newFilm);
        films.put(id, newFilm);
        return newFilm;
    }

    @Override
    public List<Film> getFilms() {
        return films.values().stream().toList();
    }

    @Override
    public Film getFilm(long filmId) {
        if (films.get(filmId) == null) {
            throw new NotFoundException("Фильма с таким id не существует");
        }
        return films.get(filmId);
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        if (updatedFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        if (films.get(updatedFilm.getId()) == null) {
            throw new NotFoundException("Фильма с таким id не существует");
        }
        if (updatedFilm.getName() == null) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (updatedFilm.getDescription() == null || updatedFilm.getDescription().length() > 200) {
            throw new ValidationException("Название фильма не может содержать больше 200 символов или быть пустым");
        }
        if (updatedFilm.getReleaseDate() == null || LocalDate.of(1895, 12, 28).isAfter(updatedFilm.getReleaseDate())) {
            throw new ValidationException("Дата релиза фильма не может быть раньше 1895 года или быть пустой");
        }
        if (updatedFilm.getDuration() <= 0) {
            throw new ValidationException("Продолжительность не может быть отрицательной или равняться нулю");
        }

        log.info("Updating film with id: {}", updatedFilm.getId());
        films.remove(updatedFilm.getId());
        films.put(updatedFilm.getId(), updatedFilm);
        log.info("Updating film with id: {}", updatedFilm);
        return updatedFilm;
    }

    @Override
    public void deleteFilm(long filmId) {
        if (films.get(filmId) == null) {
            throw new ValidationException("Фильма с таким id не существует");
        }
        log.info("Deleting film with id: {}", filmId);
        films.remove(filmId);
    }

    public void removeLike(long filmId, long userId) {
        if (films.get(filmId) == null) {
            throw new NotFoundException("Фильма с таким id не существует");
        }
        if (inMemoryUserStorage.getUser(userId) == null) {
            throw new NotFoundException("Фильма с таким id не существует");
        }
        Film film = getFilm(filmId);
        film.getLikes().remove(userId);
    }

    private long getNextId() {
        return idCounter++;
    }
}