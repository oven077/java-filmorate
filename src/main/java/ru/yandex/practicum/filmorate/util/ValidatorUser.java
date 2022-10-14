package ru.yandex.practicum.filmorate.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

public abstract class ValidatorUser {

    public static void globalCheck(User user) {
        checkEmail(user);
        checkLogin(user);
        checkBirthDay(user);
        checkEmptyName(user);
    }

    public static void checkEmail(User user) {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("incorrect email");
        }
    }

    public static void checkLogin(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("incorrect login");
        }
    }

    public static void checkBirthDay(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("incorrect birthday");
        }
    }

    public static void checkEmptyName(User user) {

        if (user.getName() == "" || user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

}
