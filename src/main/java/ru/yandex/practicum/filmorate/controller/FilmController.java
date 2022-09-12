package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.ValidateFilm;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Valid
@RestController
@Slf4j
@RequestMapping(
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {

    private static HashMap<Integer,Film> films = new HashMap<>();

    @GetMapping("/films")
    public List<Film> returnFilms() {
        // логируем факт получения запроса
        log.info("Получен запрос.");
        return  new ArrayList<Film>(films.values());
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {

        ValidateFilm.globalCheck(film);

        films.put(film.getId(), film);
        log.info("film was added");

        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (film.getId() <= 0) {
            log.info("При обновлении фильма пустой id");
            throw new ValidationException("incorrect Id for update film");
        }

        if (ValidateFilm.findFilm(film)) {
            ValidateFilm.checkReleaseDate(film);

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
