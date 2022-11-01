package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.impl.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.util.ValidatorUser;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserService {

    private UserDaoImpl userDao;

    @Autowired
    public UserService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
    public void addFriend(int id, int friendId) {
        log.info("Service:method,userService->addFriend");
        userDao.addFriend(id, friendId);
    }

    public void deleteFromFriendList(int id, int userId) {
        log.info("Service:method,userService->deleteFromFriendList");
        userDao.deleteFromFriendList(id, userId);
    }

    public List<User> returnCommonFriends(int id, int otherId) {
        log.info("Service:method,userService->returnCommonFriends");
        return userDao.returnCommonFriends(id, otherId);
    }

    public List<User> returnUserFriends(int id) {
        log.info("Service:method,userService->returnUserFriends");
        return userDao.returnFriendsUser(id);
    }

    public List<User> returnUsers() {
        log.info("Service:method,userService->returnUsers");
        return userDao.returnUsers();
    }

    public User returnUserById(int id) {
        log.info("Service:method,userService->returnUserById");
        return userDao.returnUserById(id);
    }

    public User addUser(User user) {
        log.info("Service:method,userService->addUser");
        ValidatorUser.globalCheck(user);
        return userDao.addUser(user);
    }

    public User updateUser(User user) {
        log.info("Service:method,userService->updateUser");
        ValidatorUser.globalCheck(user);
        return userDao.updateUser(user);
    }
}
