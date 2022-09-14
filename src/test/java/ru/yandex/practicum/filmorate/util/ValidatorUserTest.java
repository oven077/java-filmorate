package ru.yandex.practicum.filmorate.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorUserTest {

    private User user;
    private UserController userController;


    @BeforeEach
    void setUp() {
        user = new User(ValidatorUser.generateId(), "asd@asd.ru", "login", "name"
                , LocalDate.of(1982,04,07));

        userController = new UserController();
        userController.createUser(user);

    }

    @AfterEach
    void tearDown() {
        ValidatorUser.id = 0;
    }

    @Test
    void findUserIsTrue() {
        user = new User(1, "asd@asd.ru", "login", "name"
                , LocalDate.of(1982,04,07));
        assertEquals(true, ValidatorUser.findUser(user),"user есть в базе");
    }

    @Test
    void findUserIsFalse() {
        user = new User(3, "asd@asd.ru", "login", "name"
                , LocalDate.of(1982,04,07));
        assertEquals(false, ValidatorUser.findUser(user),"user нет в базе");
    }


    @Test
    void checkIdForUpdate() {
        user = new User(-12, "asd@asd.ru", "login", "name"
                , LocalDate.of(1982,04,07));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> ValidatorUser.checkIdForUpdate(user));
        assertEquals("incorrect id for update user", exception.getMessage());
    }


    @Test
    void checkIdWithException() {
        user = new User(1, "asd@asd.ru", "login", "name"
                , LocalDate.of(1982,04,07));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> ValidatorUser.checkId(user));
        assertEquals("Пользователь с id " + user.getId() + " уже существует в БД" +
                ", надо использовать другой метод", exception.getMessage());

    }

    @Test
    void checkIdWithSetNewId() {
        user = new User(0, "asd@asd.ru", "login", "name"
                , LocalDate.of(1982,04,07));

        ValidatorUser.checkId(user);
        assertEquals(2,user.getId(),"не совпало значение по id");

    }


    @Test
    void checkEmail() {
        user = new User(ValidatorUser.generateId(), "asdasd.ru", "login", "name"
                , LocalDate.of(1982,04,07));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> ValidatorUser.checkEmail(user));
        assertEquals("incorrect email", exception.getMessage());


    }

    @Test
    void checkLogin() {
        user = new User(ValidatorUser.generateId(), "asd@asd.ru", "log in", "name"
                , LocalDate.of(1982,04,07));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> ValidatorUser.checkLogin(user));
        assertEquals("incorrect login", exception.getMessage());


    }

    @Test
    void checkBirthDay() {

        final LocalDate BIRTHDAY = LocalDate.now().plusDays(10);

        user = new User(ValidatorUser.generateId(), "asd@asd.ru", "log in", "name"
                , BIRTHDAY);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> ValidatorUser.checkBirthDay(user));
        assertEquals("incorrect birthday", exception.getMessage());

    }

    @Test
    void checkEmptyName() {

        user = new User(ValidatorUser.generateId(), "asd@asd.ru", "login", ""
                , LocalDate.of(1982,04,07));

        ValidatorUser.checkEmptyName(user);

        assertEquals(user.getName(),"login","не совпадает login");

    }


}