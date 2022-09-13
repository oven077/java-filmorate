package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.ValidatorUser;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Valid
@RestController
@RequestMapping(
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Slf4j

public class UserController {
    private static HashMap<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public static List<User> returnUsers() {
        log.info("получен список пользователей" + users.values().toString());

        return new ArrayList<User>(users.values());
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {

        ValidatorUser.globalCheck(user);

        users.put(user.getId(), user);
        log.info("user" + user + "was added");
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {

        ValidatorUser.checkForUpdate(user);

        if (ValidatorUser.findUser(user)) {
            users.put(user.getId(), user);
        } else {
            return createUser(user);
        }
        log.info("user" + user + " was updated");
        return user;
    }

    public static HashMap<Integer, User> getUsers() {
        return users;
    }
}
