package ru.yandex.practicum.filmorate.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.AgeRestrictionsRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MpaRepositoryImpl extends BaseRepository<Mpa> implements AgeRestrictionsRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM MPA_TYPE ORDER BY MPA_ID";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM MPA_TYPE WHERE MPA_ID = ?";

    public MpaRepositoryImpl(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper, Mpa.class);
    }

    @Override
    public List<Mpa> getAllAgeRestrictions() {
        List<Mpa> mpaList = findMany(FIND_ALL_QUERY);
        log.info("Получено {} Mpa", mpaList.size());
        return mpaList;
    }

    @Override
    public Optional<Mpa> getAgeRestrictionsById(Long id) {
        log.info("Запрос на получение MPA с id: {}", id);
        return findOne(FIND_BY_ID_QUERY, id);
    }

}