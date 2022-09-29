package ru.yandex.practicum.filmorate.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;

@Slf4j
public abstract class ValidatorFilm {

    public static void globalCheck(Film film){
        checkReleaseDate(film);
    }

    public static void checkReleaseDate(Film film) {
        if ((film.getReleaseDate() != null) && (film.getReleaseDate()
                .isBefore(LocalDate.of(1895, 12, 28)))) {
            log.info("дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("incorrect ReleaseDate");
        }
    }
}



