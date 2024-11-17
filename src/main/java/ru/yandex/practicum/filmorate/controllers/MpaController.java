package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public ResponseEntity<List<Mpa>> getAllMpaRatings() {
        List<Mpa> mpaRatings = mpaService.findAllMpaRatings();
        return ResponseEntity.ok(mpaRatings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mpa> getMpaById(@PathVariable Long id) {
        Mpa mpa = mpaService.findMpaById(id);
        return ResponseEntity.ok(mpa);
    }

    @PostMapping
    public ResponseEntity<Mpa> addMpa(@RequestBody Mpa mpa) {
        Mpa addedMpa = mpaService.addMpa(mpa);
        return new ResponseEntity<>(addedMpa, HttpStatus.CREATED);
    }
}