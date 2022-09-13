package ru.yandex.practicum.filmorate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public abstract class ValidatorFilm extends FilmController {
    public static int id = 1;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static boolean findFilm(Film film) {
        if (getFilms().containsKey(film.getId())) {
            System.out.println("Найден фильм " + film);
            return true;
        }
        return false;
    }

    public static void globalCheck(Film film){
        checkId(film);
        checkReleaseDate(film);
    }
    public static void globalCheckForUpdate(Film film){
        checkIdForUpdate(film);
        checkReleaseDate(film);
    }
    public static void checkIdForUpdate(Film film) {
        if (film.getId() <= 0) {
            log.info("При обновлении фильма пустой id");
            throw new ValidationException("incorrect Id for update film");
        }
    }

    public static void checkId(Film film) {
        if (film.getId() > 0) {
            if (findFilm(film)) {
                log.info("фильм уже есть в базе");
                throw new ValidationException("Фильм с id " + film.getId() + " уже существует в БД" +
                        ", надо использовать другой метод");
            }
        } else {
            film.setId(ValidatorFilm.generateId());
        }
    }

    public static void checkReleaseDate(Film film) {
        if ((film.getReleaseDate() != null) && (film.getReleaseDate()
                .isBefore(LocalDate.of(1895, 12, 28)))) {
            log.info("дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("incorrect ReleaseDate");
        }
    }

    public static int generateId() {
        return id++;
    }

}



