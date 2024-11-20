package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UpdateFilmDto;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.GenresFilm;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.LikeRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.repository.GenresFilmRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final UserRepository userRepository;
    private final GenresFilmRepository genresFilmRepository;
    private final LikeRepository likeRepository;
    private final FilmRepository filmRepository;

    public List<FilmDto> getFilms() {
        return filmRepository.getFilms().stream().map(FilmMapper::toFilmDto).map(this::addGenresToFilmDto).toList();
    }

    public FilmDto createFilm(Film film) {
        Film newFilm = filmRepository.createFilm(film);
        addGenresToGenresFilm(newFilm.getId(), newFilm.getGenres());
        return FilmMapper.toFilmDto(newFilm);
    }

    public FilmDto updateFilm(UpdateFilmDto request) {
        if (!request.hasId()) {
            throw new InternalServerException("Не передан id фильма");
        }
        Film updateFilm = FilmMapper.updateFilmFields(getFilmById(request.getId()), request);
        updateFilm = filmRepository.updateFilm(updateFilm);
        return FilmMapper.toFilmDto(updateFilm);
    }

    public FilmDto addLike(Long filmId, Long userId) {
        validateUserExists(userId);
        Film film = getFilmById(filmId);
        Like like = likeRepository.addLikeToFilm(filmId, userId);
        FilmDto response = FilmMapper.toFilmDto(film);
        response.setLikes(Set.of(like.getUserId()));
        return response;
    }

    public FilmDto deleteLike(Long filmId, Long userId) {
        validateUserExists(userId);
        Film film = getFilmById(filmId);
        likeRepository.deleteLike(filmId, userId);
        return FilmMapper.toFilmDto(film);
    }

    public List<FilmDto> getMostPopularByNumberOfLikes(Long count) {
        return filmRepository.getMostPopularByNumberOfLikes(count).stream()
                .map(FilmMapper::toFilmDto)
                .map(this::addGenresToFilmDto)
                .toList();
    }

    public FilmDto getWithGenre(Long id) {
        FilmDto filmDto = FilmMapper.toFilmDto(getFilmById(id));
        addGenresToFilmDto(filmDto);
        return filmDto;
    }

    private void addGenresToGenresFilm(Long filmId, List<Genre> genresList) {
        if (genresList != null) {
            Set<Genre> uniqueGenres = new LinkedHashSet<>(genresList);
            uniqueGenres.forEach(genre -> genresFilmRepository.addGenreToFilm(filmId, genre.getId()));
        }
    }

    private void validateUserExists(Long userId) {
        userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id: " + userId + " не найден"));
    }

    private Film getFilmById(Long filmId) {
        return filmRepository.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id: " + filmId + " не найден"));
    }

    private FilmDto addGenresToFilmDto(FilmDto filmDto) {
        List<Genre> filmGenresList = genresFilmRepository.getGenresByFilmId(filmDto.getId()).stream()
                .map(GenresFilm::getGenre)
                .toList();
        filmDto.setGenres(filmGenresList);
        return filmDto;
    }
}