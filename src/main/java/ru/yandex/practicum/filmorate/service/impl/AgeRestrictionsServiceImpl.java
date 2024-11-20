package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.impl.MpaRepositoryImpl;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AgeRestrictionsServiceImpl implements AgeRestrictionsService {

    private final MpaRepositoryImpl ageRestrictionsRepository;

    public List<Mpa> getAllAgeRestrictions() {
        return ageRestrictionsRepository.getAllAgeRestrictions();
    }

    public Mpa getAgeRestrictionsById(@PathVariable Long id) {
        return ageRestrictionsRepository.getAgeRestrictionsById(id)
                .orElseThrow(() -> new NotFoundException("Mpa с таким id: " + id + " не найден"));
    }
}