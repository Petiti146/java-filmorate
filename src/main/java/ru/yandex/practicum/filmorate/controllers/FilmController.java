package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmDto;
import ru.yandex.practicum.filmorate.service.impl.FilmServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmServiceImpl filmService;
    private static final String DEFAULT_POPULAR_MOVIES_DISPLAYED_COUNT = "10";

    @GetMapping
    public List<FilmDto> findAll() {
        return filmService.getFilms();
    }

    @PostMapping
    public FilmDto create(@Valid @RequestBody FilmDto film) {
        return filmService.createFilm(film);
    }

    @PutMapping
    public FilmDto update(@RequestBody UpdateFilmDto request) {
        return filmService.updateFilm(request);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmDto deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<FilmDto> getMostPopularByNumberOfLikes(
            @RequestParam(required = false, defaultValue = DEFAULT_POPULAR_MOVIES_DISPLAYED_COUNT) Long count) {
        return filmService.getMostPopularByNumberOfLikes(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public FilmDto addLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.addLike(id, userId);
    }

    @GetMapping("/{id}")
    public FilmDto getWithGenre(@PathVariable Long id) {
        return filmService.getWithGenre(id);
    }
}