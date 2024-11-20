package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {
    @Max(message = "id не может быть больше 6", value = 6)
    private Long id;
    private String name;
}