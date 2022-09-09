package ru.yandex.practicum.filmorate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public abstract class Validate extends UserController {
    public static int id = 1;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static boolean findUser(User user) {
        if (getUsers().containsKey(user.getId())) {
            System.out.println("Найден пользователь " + user);
            return true;
        }
        return false;
    }

    public static void checkEmail(User user) {
        if (user.getEmail().isEmpty() && !user.getEmail().contains("@")) {
            log.info("Поле email не по стандарту");
            throw new ValidationException("incorrect email");
        }
    }

    public static void checkLogin(User user) {
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.info("Поле login не по стандарту");
            throw new ValidationException("incorrect login");
        }
    }

    public static void checkBirthDay(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Поле birthday не по стандарту");
            throw new ValidationException("incorrect birthday");
        }
    }

    public static int generateId() {
        return id++;
    }

}
