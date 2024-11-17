package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaService implements MpaServiceInterface {

    private final MpaStorage mpaStorage;

    @Override
    public List<Mpa> findAllMpaRatings() {
        return mpaStorage.findAll();
    }

    @Override
    public Mpa findMpaById(Long id) {
        return mpaStorage.findById(id)
                .orElseThrow(() -> new RuntimeException("MPA не найден с ID: " + id));
    }

    @Override
    public Mpa addMpa(Mpa mpa) {
        return mpaStorage.save(mpa);
    }
}