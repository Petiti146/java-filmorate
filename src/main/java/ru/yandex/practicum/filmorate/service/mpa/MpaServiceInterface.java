package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.models.Mpa;

import java.util.List;

public interface MpaServiceInterface {

    List<Mpa> findAllMpaRatings();

    Mpa findMpaById(Long id);

    Mpa addMpa(Mpa mpa);
}