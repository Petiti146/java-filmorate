package ru.yandex.practicum.filmorate.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.repository.FriendRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FriendRepositoryImpl extends BaseRepository<Friendship> implements FriendRepository {
    private static final String INSERT_FRIEND = "INSERT INTO FRIENDSHIP(user_id, friend_id, accept) VALUES (?, ?, ?)";
    private static final String FIND_FRIEND_BY_ID = "SELECT * FROM FRIENDSHIP WHERE USER_ID = ? AND FRIEND_ID = ?";
    private static final String FIND_ALL_FRIEND = "SELECT * FROM FRIENDSHIP WHERE USER_ID = ?";
    private static final String UPDATE_FRIEND_BY_ID = "UPDATE FRIENDSHIP SET ACCEPT = ? " +
            "WHERE USER_ID = ? AND FRIEND_ID = ?";
    private static final String DELETE_FRIEND = "DELETE FROM FRIENDSHIP WHERE USER_ID = ? AND FRIEND_ID = ?";
    private static final String FIND_MUTUAL_FRIENDS = "SELECT * FROM FRIENDSHIP WHERE USER_ID = ? AND FRIEND_ID " +
            "IN (SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID = ?)";

    public FriendRepositoryImpl(JdbcTemplate jdbc, RowMapper<Friendship> mapper) {
        super(jdbc, mapper, Friendship.class);
    }

    @Override
    public Friendship addFriend(Long userId, Long friendId, boolean accept) {
        Friendship friendship = Friendship.builder()
                .userId(userId)
                .friendId(friendId)
                .accept(accept)
                .build();
        int id = jdbc.update(
                INSERT_FRIEND,
                userId,
                friendId,
                accept
        );
        log.info("Друг успешно добавлен: {}", friendship);
        return friendship;
    }

    @Override
    public List<Friendship> findAllFriends(Long userId) {
        List<Friendship> friends = findMany(FIND_ALL_FRIEND, userId);
        log.info("Найдено {} друзей для userId={}", friends.size(), userId);
        return friends;
    }

    @Override
    public Optional<Friendship> findFriendById(Long userId, Long friendId) {
        log.info("Запрос друга по ID : userId={}, friendId={}", userId, friendId);
        return findOne(FIND_FRIEND_BY_ID, userId, friendId);
    }


    @Override
    public boolean delete(Long userId, Long friendId) {
        boolean result = delete(DELETE_FRIEND, userId, friendId);
        if (result) {
            log.info("Друг успешно удален : userId={}, friendId={}", userId, friendId);
        }
        return result;
    }


    @Override
    public Friendship updateFriendStatus(Long userId, Long friendId, boolean accept) {
        Friendship friendship = Friendship.builder()
                .userId(userId)
                .friendId(friendId)
                .accept(accept)
                .build();
        update(UPDATE_FRIEND_BY_ID,
                accept,
                userId,
                friendId
        );
        log.info("Статус дружбы успешно обновлен: {}", friendship);
        return friendship;
    }

    @Override
    public List<Friendship> findMutualFriends(Long userId, Long friendId) {

        List<Friendship> mutualFriends = findMany(FIND_MUTUAL_FRIENDS, userId, friendId);
        log.info("Найдено {} взаимных друзей между userId={} и friendId={}", mutualFriends.size(), userId, friendId);
        return mutualFriends;
    }
}