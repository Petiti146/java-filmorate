package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 1;

    @Override
        public User addUser(User newUser) {
            if (newUser.getEmail() == null || !newUser.getEmail().contains("@")) {
                throw new ValidationException("Имейл пустой, либо в нем отсутствует знак <@>");
            }
            if (newUser.getLogin() == null || newUser.getLogin().contains(" ")) {
                throw new ValidationException("Логин не должен быть пустым и не должен содержать пробелы");
            }
            if (newUser.getBirthday() == null || newUser.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Ваша дата рождения указана не коректно или не указана вовсе");
            }

            log.info("Adding new user: {}", newUser);

            for (User user : users.values()) {
                if (user.getEmail().equals(newUser.getEmail())) {
                    throw new ValidationException("Этот имейл уже используется");
                }
            }
            if (newUser.getName() == null) {
                newUser.setName(newUser.getLogin());
            }
            long id = getNextId();
            newUser.setId(id);
            users.put(id, newUser);

            return newUser;
        }

    public void addFriend(long userId, long friendId) {
        if (users.get(userId) == null || users.get(friendId) == null) {
            throw new NotFoundException("User or friend not found");
        }
        User user = users.get(userId);
        User friend = users.get(friendId);
        if (user.getFriends().contains(friendId)) {
            throw new ValidationException("Пользователь уже добавлен в друзья");
        }
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    @Override
    public List<User> getUsers() {
        return users.values().stream().toList();
    }

    @Override
    public User getUser(long id) {
        if (users.get(id) == null) {
            throw new NotFoundException("Пользователь с таким id не существует");
        }
        return users.get(id);
    }

    public List<User> getCommonFriends(long userId, long otherUserId) {
        if (users.get(userId) == null || users.get(otherUserId) == null) {
            throw new NotFoundException("Пользователь или другой пользователь не найден");
        }

        User user = users.get(userId);
        User otherUser = users.get(otherUserId);

        // Логируем идентификаторы друзей
        System.out.println("User " + userId + " friends: " + user.getFriends());
        System.out.println("User " + otherUserId + " friends: " + otherUser.getFriends());

        Set<Long> userFriends = new HashSet<>(user.getFriends());
        Set<Long> otherUserFriends = new HashSet<>(otherUser.getFriends());

        // Пересечение множеств
        userFriends.retainAll(otherUserFriends);

        // Логируем результат пересечения
        System.out.println("Common friends count: " + userFriends.size());

        // Create a list of common friends
        List<User> commonFriends = new ArrayList<>();
        for (Long friendId : userFriends) {
            User friend = users.get(friendId);
            if (friend != null) {
                commonFriends.add(friend);
            }
        }

        return commonFriends;
    }

    @Override
    public List<User> getFriends(long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        List<User> friends = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            friends.add(users.get(friendId));
        }
        return friends;
    }

    @Override
    public User updateUser(User updatedUser) {
        if (updatedUser.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        if (users.get(updatedUser.getId()) == null) {
            throw new NotFoundException("Пользователь с таким id не существует");
        }
        if (updatedUser.getEmail() == null || !updatedUser.getEmail().contains("@")) {
            throw new ValidationException("Имейл пустой, либо в нем отсутствует знак <@>");
        }
        if (updatedUser.getLogin() == null || updatedUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен быть пустым и не должен содержать пробелы");
        }
        if (updatedUser.getBirthday() == null || updatedUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Ваша дата рождения указана не коректно или не указана вовсе");
        }

        log.info("Updating user with id: {}", updatedUser.getId());
        users.remove(updatedUser.getId());
        users.put(updatedUser.getId(), updatedUser);
        return updatedUser;
    }

    @Override
    public User deleteUser(long userId) {
        if (users.get(userId) == null) {
            throw new ValidationException("Пользователя с таким id не существует");
        }
        log.info("Deleting film with id: {}", userId);
        return users.remove(userId);
    }

    public void removeFriend(long userId, long friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);
        if (user == null || friend == null) {
            throw new NotFoundException("User or friend not found");
        }
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    private long getNextId() {
        return idCounter++;
    }
}
