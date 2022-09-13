package ru.yandex.practicum.filmorate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public abstract class ValidatorUser extends UserController {
    public static int id = 1;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static void globalCheck(User user) {
        checkId(user);
        checkEmail(user);
        checkLogin(user);
        checkBirthDay(user);
        checkEmptyName(user);
    }

    public static void checkForUpdate(User user) {
        checkIdForUpdate(user);
        checkEmail(user);
        checkLogin(user);
        checkBirthDay(user);
        checkEmptyName(user);
    }

    public static void checkIdForUpdate(User user) {
        if (user.getId() <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new ValidationException("incorrect id for update user");
        }
    }

    public static boolean findUser(User user) {
        if (getUsers().containsKey(user.getId())) {
            System.out.println("Найден пользователь " + user);
            return true;
        }
        return false;
    }

    public static void checkId(User user) {
        if (user.getId() > 0) {
            if (findUser(user)) {
                log.info("пользователь уже есть в базе");
                throw new ValidationException("Пользователь с id " + user.getId() + " уже существует в БД" +
                        ", надо использовать другой метод");
            }
        } else {
            user.setId(ValidatorUser.generateId());
        }
    }

    public static void checkEmail(User user) {
        if (!user.getEmail().contains("@")) {
            log.info("Поле email не по стандарту");
            throw new ValidationException("incorrect email");
        }
    }

    public static void checkLogin(User user) {
        if (user.getLogin().contains(" ")) {
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

    public static void checkEmptyName(User user) {

        if (user.getName() == "" || user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    public static int generateId() {
        return id++;
    }

}
