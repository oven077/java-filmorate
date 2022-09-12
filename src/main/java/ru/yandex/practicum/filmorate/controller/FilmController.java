package ru.yandex.practicum.filmorate.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.ValidateFilm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
@RestController
@RequestMapping(
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static HashMap<Integer,Film> films = new HashMap<>();

    @GetMapping("/films")
    public List<Film> returnFilms() {
        // логируем факт получения запроса
        log.info("Получен запрос.");
        return  new ArrayList<Film>(films.values());
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody @Validated @NotNull Film film) {

        ValidateFilm.checkId(film);
        ValidateFilm.checkName(film);
        ValidateFilm.checkReleaseDate(film);
        ValidateFilm.checkMaxLengthDescription(film);
        ValidateFilm.checkDuration(film);

        films.put(film.getId(), film);
        log.info("film has added");

        return film;
    }


    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        if (film.getId() <= 0) {
            log.info("При обновлении фильма пустой id");
            throw new ValidationException("incorrect Id for update film");
        }

        if (ValidateFilm.findFilm(film)) {
            ValidateFilm.checkName(film);
            ValidateFilm.checkMaxLengthDescription(film);
            ValidateFilm.checkReleaseDate(film);
            ValidateFilm.checkDuration(film);

            films.put(film.getId(), film);

        } else {
            return createFilm(film);
        }
        return film;
    }

    public static HashMap<Integer, Film> getFilms() {
        return films;
    }


}
