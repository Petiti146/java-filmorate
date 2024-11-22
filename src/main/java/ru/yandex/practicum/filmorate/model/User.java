package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class User {
    private Long id;

    @NotNull(message = "Имейл должен быть указан")
    @Email(message = "Имейл должен содержать символ '@'")
    private String email;

    @NotNull(message = "Логин должен быть указан")
    @Pattern(regexp = "\\S+", message = "Логин не должен содержать символ пробела")
    private String login;

    private String name;

    @Past(message = "Дата рождения не должна быть позже текущей даты")
    private LocalDate birthday;

    private final List<Long> friends = new ArrayList<>();

}