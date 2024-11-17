package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.models.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {
    List<Mpa> findAll();
    Optional<Mpa> findById(Long id);
    Mpa save(Mpa mpa);
}