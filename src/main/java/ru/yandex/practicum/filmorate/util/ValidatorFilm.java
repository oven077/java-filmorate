package ru.yandex.practicum.filmorate.util;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public abstract class ValidatorFilm {

    public static void globalCheck(Film film){
        checkReleaseDate(film);
    }

    public static void checkReleaseDate(Film film) {
        if ((film.getReleaseDate() != null) && (film.getReleaseDate()
                .isBefore(LocalDate.of(1895, 12, 28)))) {
            throw new ValidationException("incorrect ReleaseDate");
        }
    }
}



