package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
@RestController
@RequestMapping(
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {

    // создаём логер
    private static final Logger log = LoggerFactory.getLogger(UserController.class);



    private static HashMap<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public static List<User> returnUsers() {
        // логируем факт получения запроса
        log.info("Получен запрос.");
        return  new ArrayList<User>(users.values());
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public User createUser(@RequestBody User user) {

        if (user.getId() > 0) {
            if (Validate.findUser(user)) {
                log.info("пользователь уже есть в базе");
                throw new ValidationException("Пользователь с id " + user.getId() + " уже существует в БД" +
                        ", надо использовать другой метод");
            }
        }

        Validate.checkEmail(user);
        Validate.checkLogin(user);
        Validate.checkBirthDay(user);

        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        if(user.getId()==0){
            user.setId(Validate.generateId());
        }

        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {

        if (user.getId() <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new ValidationException("incorrect Id for update user");
        }

        if (Validate.findUser(user)) {
            Validate.checkEmail(user);
            Validate.checkLogin(user);
            Validate.checkBirthDay(user);

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
