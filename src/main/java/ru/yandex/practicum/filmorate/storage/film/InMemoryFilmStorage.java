package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Long, Film> films = new HashMap<>();
    private long idCounter = 1;

    @Override
    public Film addFilm(Film newFilm) {
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

        log.info("Adding new film: {}", newFilm);
        long id = getNextId();
        newFilm.setId(id);
        films.put(id, newFilm);
        return newFilm;
    }

    @Override
    public void addLike(long filmId, long userId) {
        if (films.get(filmId) == null) {
            throw new ValidationException("Фильма с таким id не существует");
        }
        Film film = films.get(filmId);
        if (film.getLikes().contains(userId)) {
            throw new ValidationException("Пользователь уже поставил лайк этому фильму");
        }
        film.getLikes().add(userId);
        updateFilm(film);
    }

    @Override
    public List<Film> getFilms() {
        return films.values().stream().toList();
    }

    @Override
    public Film getFilm(long filmId) {
        if (films.get(filmId) == null) {
            throw new ValidationException("Фильма с таким id не существует");
        }
        return films.get(filmId);
    }

    @Override
    public List<Film> getMostPopularFilms(int count) {
        // Если count не задан или меньше 1, задаем его значение на 10
        int limit = count > 0 ? count : 10;

        // Получаем список фильмов
        List<Film> films = getFilms();

        // Проверка, что список фильмов не пустой
        if (films == null || films.isEmpty()) {
            throw new NotFoundException("Фильмов нет");
        }

        // Проверка, если фильмов меньше, чем запрошенный limit
        limit = Math.min(limit, films.size());

        // Сортируем фильмы по количеству лайков (от большего к меньшему)
        films.sort((f1, f2) -> f2.getLikes().size() - f1.getLikes().size());

        // Возвращаем первые 'limit' фильмов
        return films.subList(0, limit);
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
        return updatedFilm;
    }

    @Override
    public Film deleteFilm(long filmId) {
        if (films.get(filmId) == null) {
            throw new ValidationException("Фильма с таким id не существует");
        }
        log.info("Deleting film with id: {}", filmId);
        return films.remove(filmId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        if (films.get(filmId) == null) {
            throw new ValidationException("Фильма с таким id не существует");
        }
        Film film = getFilm(filmId);
        film.getLikes().remove(userId);
    }

    private long getNextId() {
        return idCounter++;
    }
}