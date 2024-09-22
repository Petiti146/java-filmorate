package ru.yandex.practicum.filmorate.controllertests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTests {
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    @Test
    public void testAddFilmValidationExceptionNameEmpty() {
        InMemoryFilmStorage filmController = new InMemoryFilmStorage(inMemoryUserStorage);
        Film film = new Film(null, "Test Description", LocalDate.of(2021, 10, 1), 2222);

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testAddFilmValidationExceptionReleaseDateBefore1895() {
        InMemoryFilmStorage filmController = new InMemoryFilmStorage(inMemoryUserStorage);
        Film film = new Film("Test Film", "Test Description", LocalDate.of(1800, 1, 1), 2222);

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testAddFilmValidationExceptionDurationNegative() {
        InMemoryFilmStorage filmController = new InMemoryFilmStorage(inMemoryUserStorage);
        Film film = new Film("Test Film", "Test Description", LocalDate.of(2021, 10, 1), -1);

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }
}
