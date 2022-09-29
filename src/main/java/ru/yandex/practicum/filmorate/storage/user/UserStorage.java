package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserStorage {

    User addUser(User user);

    ArrayList<User> returnUsers();

    User updateUser(User user);

    User returnUserById(int id);

    public void addFriend(int id, int friendId);

    public void deleteFromFriendList(int id, int userId);

    public List<User> returnCommonFriends(int id, int otherId);

    public List<User> returnFriendsUser(int id);


}
