package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Valid
@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    //get all films
    @GetMapping()
    public List<Film> returnFilms() {
        return filmService.returnFilms();
    }

    //пet film by id
    @GetMapping("/{id}")
    public Film returnFilmById(@PathVariable @Min(1) Integer id) {
        return filmService.returnFilmById(id);
    }


    //add film
    @PostMapping()
    @ResponseBody
    public Film addFilm(@Valid @RequestBody(required = false) Film film) {
        filmService.addFilm(film);
        return film;
    }

    //update film
    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        return film;
    }

    //user set like for film
    @PutMapping("/{id}/like/{userId}")
    public void addFilmLike(@PathVariable int id,
                            @PathVariable int userId
    ) {
        filmService.addLike(id, userId);
    }

    //user remove like for film
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteFilmLike(@PathVariable int id,
                               @PathVariable int userId
    ) {
        filmService.deleteLike(id, userId);
    }

    //get top list films
    @GetMapping("/popular")
    public List<Film> popular(@RequestParam(required = false, defaultValue = "10") int count) {
        return filmService.returnTopFilms(count);
    }


}
