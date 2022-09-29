package ru.yandex.practicum.filmorate.storage.user;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.ValidatorUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Setter
@Getter
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    public HashMap<Integer, User> users = new HashMap<>();
    private static int id = 0;

    @Override
    public User addUser(User user) {
        ValidatorUser.globalCheck(user);
        if (user.getId() > 0) {
            if (findUser(user)) {
                log.info("пользователь уже есть в базе");
                throw new ValidationException("Пользователь с id " + user.getId() + " уже существует в БД" +
                        ", надо использовать другой метод");
            }
        } else if (user.getId() == 0) {
            user.setId(generateId());
        }

        users.put(user.getId(), user);
        return user;

    }

    @Override
    public ArrayList<User> returnUsers() {
        log.info("Вернули список пользователей" + new ArrayList<>(users.values()));
        return new ArrayList<>(users.values());
    }

    @Override
    public User updateUser(User user) {
        checkUserId(user);
        if (findUser(user)) {
            users.put(user.getId(), user);
        } else {
            return addUser(user);
        }
        return user;
    }


    public boolean findUser(User user) {

        if (this.users.containsKey(user.getId())) {
            System.out.println("Найден пользователь " + user);
            return true;
        }
        return false;
    }

    @Override
    public User returnUserById(int id) {

        checkId(id);
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            log.info("Некорректный id");
            throw new BadRequestException("incorrect id for update user");
        }
    }

    @Override
    public void addFriend(int id, int friendId) {
        checkId(id);
        checkId(friendId);
        if (returnUserById(id) != null && returnUserById(friendId) != null) {
            returnUserById(id)
                    .getFriends()
                    .add(friendId);
            returnUserById(friendId)
                    .getFriends()
                    .add(id);

        } else {
            throw new BadRequestException("Пользователь с id " + id + " не найден");
        }
    }

    @Override
    public void deleteFromFriendList(int id, int userId) {
        checkId(id);
        checkId(userId);
        if (returnUserById(id) != null) {
            returnUserById(id)
                    .getFriends()
                    .remove(userId);
        } else {
            throw new BadRequestException("Пользователь с id " + id + " не найден");
        }
    }

    @Override
    public List<User> returnCommonFriends(int id, int otherId) {
        checkId(id);
        checkId(otherId);
        return returnUserById(id).getFriends().stream()
                .filter(idUser -> returnUserById(otherId).getFriends().contains(idUser))
                .map(idUser -> returnUserById(idUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> returnFriendsUser(int id) {
        checkId(id);
        if (returnUserById(id) != null) {
            return returnUserById(id).getFriends().stream()
                    .map(userId -> returnUserById(userId))
                    .collect(Collectors.toList());
        } else {
            throw new BadRequestException("Пользователь с id " + id + " не найден");
        }
    }

    public static void checkUserId(User user) {
        if (user.getId() <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new BadRequestException("incorrect id for update user");
        }
    }

    public static void checkId(int id) {
        if (id <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new BadRequestException("incorrect id for update user");
        }
    }

    public int generateId() {
        id++;
        return id;
    }



}
