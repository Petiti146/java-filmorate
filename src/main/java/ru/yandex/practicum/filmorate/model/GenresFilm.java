package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenresFilm {
    private Long filmId;
    private Genre genre;
}