package ru.yandex.practicum.filmorate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public abstract class ValidateFilm extends FilmController {
    public static int id = 1;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static boolean findFilm(Film film) {
        if (getFilms().containsKey(film.getId())) {
            System.out.println("Найден фильм " + film);
            return true;
        }
        return false;
    }

    public static void checkId(Film film) {
        if (film.getId() > 0) {
            if (findFilm(film)) {
                log.info("фильм уже есть в базе");
                throw new ValidationException("Фильм с id " + film.getId() + " уже существует в БД" +
                        ", надо использовать другой метод");
            }
        } else {
            film.setId(ValidateFilm.generateId());
        }
    }


    public static void checkName(Film film) {
        if (film.getName() == "") {
            log.info("название фильма не может быть пустым");
            throw new ValidationException("incorrect film name");
        }
    }

    public static void checkMaxLengthDescription(Film film) {
        if ((film.getDescription()!= null) && (film.getDescription().length() > 200)) {
            log.info("описание фильма не может быть больше 200 символов");
            throw new ValidationException("incorrect length of description");
        }
    }

    public static void checkReleaseDate(Film film) {
        if ((film.getReleaseDate()!= null) && (film.getReleaseDate()
                .isBefore(LocalDate.of(1895,12,28)))) {
            log.info("дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("incorrect ReleaseDate");
        }
    }

    public static void checkDuration(Film film) {
        if (film.getDuration() <= 0) {
            log.info("продолжительность фильма должна быть положительной");
            throw new ValidationException("incorrect Duration");
        }
    }


    public static int generateId() {
        return id++;
    }

}



