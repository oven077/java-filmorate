package ru.yandex.practicum.filmorate.util;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

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
