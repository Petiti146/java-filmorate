package ru.yandex.practicum.filmorate.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UpdateUserDto;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public List<UserDto> getUsers() {
        return userRepository.getUsers().stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        User user = findUserById(id); // Получаем пользователя по id
        return createUserDtoWithFriends(user); // Возвращаем пользователя с друзьями
    }

    public UserDto userCreate(@Valid UserDto user) {
        //return UserMapper.toUser(userRepository.userCreate(user));
        User userDto = UserMapper.toUser(user); // Преобразование UserDto в User
        User user2  = userRepository.userCreate(userDto); // Сохранение нового пользователя
        return UserMapper.mapToUserDto(user2);
    }

    public UserDto userUpdate(UpdateUserDto request) {
        validateRequest(request);
        User user = findUserById(request.getId()); // Получаем пользователя по id из запроса
        User updateUser = UserMapper.updateUserFields(user, request);
        updateUser = userRepository.userUpdate(updateUser); // Обновляем пользователя в хранилище
        return UserMapper.mapToUserDto(updateUser); // Возвращаем обновленного пользователя в формате UserDto
    }

    public UserDto friending(Long userId, Long friendId) {
        UserDto response = UserMapper.mapToUserDto(findUserById(userId));
        findUserById(friendId);
        List<Friendship> userList = friendRepository.findAllFriends(friendId);
        List<Friendship> friendList = friendRepository.findAllFriends(userId);
        if (checkFriendshipExists(userList, userId, friendId)) {
            throw new InternalServerException("Пользователь с id: " + userId + " уже добавлял пользователя с id: "
                    + friendId + " в друзья");
        }
        boolean accept = checkAndUpdateFriendshipStatus(userId, friendId, friendList, true);
        Friendship friendship = friendRepository.addFriend(userId, friendId, accept);
        userList.add(friendship);
        response.setFriends(userList.stream()
                .map(Friendship::getFriendId)
                .toList()
        );
        return response;
    }

    public UserDto unfriending(Long userId, Long friendId) {
        UserDto response = UserMapper.mapToUserDto(findUserById(userId));
        findUserById(friendId);
        List<Friendship> userList = friendRepository.findAllFriends(userId);
        List<Friendship> friendList = friendRepository.findAllFriends(userId);
        if (!checkFriendshipExists(userList, userId, friendId)) {
            return response;
        }
        checkAndUpdateFriendshipStatus(userId, friendId, friendList, false);
        if (friendRepository.delete(userId, friendId)) {
            return response;
        }
        throw new InternalServerException("Не удалось удалить друга с id: " + friendId);
    }

    public List<UserDto> getUserFriends(Long userId) {
        findUserById(userId);
        return listFriendshipToListUserDto(friendRepository.findAllFriends(userId));
    }

    public List<UserDto> listOfMutualFriends(Long userId, Long friendId) {
        return listFriendshipToListUserDto(friendRepository.findMutualFriends(userId, friendId));
    }

    private User findUserById(Long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id: " + id + " не найден"));
    }

    private UserDto createUserDtoWithFriends(User user) {
        List<Friendship> friendships = friendRepository.findAllFriends(user.getId());
        UserDto userDto = UserMapper.mapToUserDto(user);
        userDto.setFriends(friendships.stream().map(Friendship::getFriendId).toList());
        return userDto;
    }

    private void validateRequest(UpdateUserDto request) {
        if (!request.hasId()) {
            throw new NotFoundException("Не передан id пользователя");
        }
    }

    private boolean checkFriendshipExists(List<Friendship> userFriends, Long userId, Long friendId) {
        return userFriends.stream().anyMatch(friend -> friend.getFriendId().equals(friendId));
    }

    private boolean checkAndUpdateFriendshipStatus(Long userId, Long friendId, List<Friendship> friendFriends,
                                                   boolean accept) {

        if (friendFriends.stream().anyMatch(friend -> friend.getFriendId().equals(userId))) {
            friendRepository.updateFriendStatus(friendId, userId, accept);
        } else {
            accept = !accept;
        }
        return accept;
    }

    private List<UserDto> listFriendshipToListUserDto(List<Friendship> list) {
        return list.stream()
                .map(Friendship::getFriendId)
                .map(userRepository::getUserById)
                .flatMap(Optional::stream)
                .map(UserMapper::mapToUserDto)
                .toList();
    }
}