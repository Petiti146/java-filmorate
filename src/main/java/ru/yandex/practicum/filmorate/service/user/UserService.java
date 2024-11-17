package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserStorage userStorage;

    @Override
    public void addFriend(long userId, long friendId) {
        userStorage.addFriend(userId, friendId);
    }

    @Override
    public void sendFriendRequest(long userId, long friendId) {
        userStorage.addFriend(userId, friendId);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userStorage.getAllUsers();
    }

    @Override
    public void acceptFriendRequest(long userId, long friendId) {
        userStorage.addFriend(userId, friendId);
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        userStorage.removeFriend(userId, friendId);
    }

    @Override
    public List<User> getCommonFriends(long userId, long otherUserId) {
        User user = userStorage.getUserById(userId);
        User otherUser = userStorage.getUserById(otherUserId);

        Set<Long> userFriends = new HashSet<>(user.getFriends());
        Set<Long> otherUserFriends = new HashSet<>(otherUser.getFriends());

        userFriends.retainAll(otherUserFriends);
        List<User> commonFriends = new ArrayList<>();
        for (Long friendId : userFriends) {
            User friend = userStorage.getUserById(friendId);
            if (friend != null) {
                commonFriends.add(friend);
            }
        }
        return commonFriends;
    }
}