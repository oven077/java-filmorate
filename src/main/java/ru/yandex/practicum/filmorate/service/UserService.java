package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.util.ValidatorUser;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    private InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.inMemoryUserStorage = (InMemoryUserStorage) userStorage;
    }

    public void addFriend(int id, int friendId) {
        inMemoryUserStorage.addFriend(id, friendId);
    }

    public void deleteFromFriendList(int id, int userId) {
        //TODO проверить на пользователя
        inMemoryUserStorage.deleteFromFriendList(id, userId);
    }

    public List<User> returnCommonFriends(int id, int otherId) {
        return inMemoryUserStorage.returnCommonFriends(id, otherId);
    }

    public List<User> returnUserFriends(int id) {
        return inMemoryUserStorage.returnFriendsUser(id);
    }

    public ArrayList<User> returnUsers() {
        return inMemoryUserStorage.returnUsers();
    }

    public User returnUserById(int id) {
        return inMemoryUserStorage.returnUserById(id);
    }

    public User addUser(User user) {
        return inMemoryUserStorage.addUser(user);
    }

    public User updateUser(User user) {
        ValidatorUser.globalCheck(user);
        return inMemoryUserStorage.updateUser(user);
    }
}
