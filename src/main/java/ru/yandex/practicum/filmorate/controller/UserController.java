package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.ValidateUser;

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
        // логируем факт получения запроса
        log.info("Получен запрос.");
        return  new ArrayList<User>(users.values());
    }

    @PostMapping("/users")
    public User createUser (@Valid @RequestBody User user) {

        ValidateUser.globalCheck(user);

        users.put(user.getId(), user);
        log.info("user was added");
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {

        if (user.getId() <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new ValidationException("incorrect Id for update user");
        }

        if (ValidateUser.findUser(user)) {
            ValidateUser.globalCheck(user);

            users.put(user.getId(), user);
        } else {
           return createUser(user);
        }
        return user;
    }

    public static HashMap<Integer, User> getUsers() {
        return users;
    }
}
