package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mpa {
    @Max(message = "id не может быть больше 5", value = 5)
    private Long id;
    private String name;
}