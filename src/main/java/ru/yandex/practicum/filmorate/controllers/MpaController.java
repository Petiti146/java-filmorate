package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.impl.AgeRestrictionsService;
import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final AgeRestrictionsService ageRestrictionsService;

    @GetMapping("/{id}")
    public Mpa getAgeRestrictionsById(@PathVariable Long id) {
        return ageRestrictionsService.getAgeRestrictionsById(id);
    }

    @GetMapping
    public List<Mpa> getAllAgeRestrictions() {
        return ageRestrictionsService.getAllAgeRestrictions();
    }
}