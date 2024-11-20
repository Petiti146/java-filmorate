package ru.yandex.practicum.filmorate.service.impl;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface AgeRestrictionsService {
    Mpa getAgeRestrictionsById(Long id);

    List<Mpa> getAllAgeRestrictions();
}
