package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.MinDate;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class FilmDto {

    public static final int MAXIMUM_DESCRIPTION_LENGTH = 200;

    private static final String MIN_RELEASE_DATE = "1895-12-28";

    private Long id;

    @NotNull(message = "Название фильма должно быть указано")
    @NotBlank(message = "Название фильма должно быть не пустым и не состоять только из пробелов")
    private String name;

    @Size(max = MAXIMUM_DESCRIPTION_LENGTH, message = "Длинна описания не должна превышать " +
            MAXIMUM_DESCRIPTION_LENGTH + " символов")
    private String description;

    @MinDate(message = "Дата релиза не может быть раньше: {value}", value = MIN_RELEASE_DATE)
    private LocalDate releaseDate;

    @PositiveOrZero(message = "Продолжительность фильма должна быть положительной")
    private Integer duration;

    private Set<Long> likes = new HashSet<>();

    private List<@Valid Genre> genres;

    @Valid
    private Mpa mpa;
}