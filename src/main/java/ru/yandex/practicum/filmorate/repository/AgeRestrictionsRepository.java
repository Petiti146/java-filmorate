package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface AgeRestrictionsRepository {
    List<Mpa> getAllAgeRestrictions();

    Optional<Mpa> getAgeRestrictionsById(Long id);
}
