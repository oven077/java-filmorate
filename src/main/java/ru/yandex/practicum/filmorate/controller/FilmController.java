package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.ValidatorFilm;

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

    private static HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public List<Film> returnFilms() {
        // логируем факт получения запроса
        log.info("Получен список фильмов " + films.values().toString());
        return new ArrayList<Film>(films.values());
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {

        ValidatorFilm.globalCheck(film);

        films.put(film.getId(), film);
        log.info("film" + film + " was added");

        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {

        ValidatorFilm.checkIdForUpdate(film);

        if (ValidatorFilm.findFilm(film)) {
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
