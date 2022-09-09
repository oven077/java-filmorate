package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

@RestController
public class FilmController {

    private HashMap<Integer,Film> films = new HashMap<>();

    @GetMapping("/films")
    public HashMap<Integer,Film> getFilms() {
        return films;
    }

    @PostMapping("/films")
    public void createFilm(@RequestBody Film film) {
//        films.put(film.getId(),film);
    }


    @PutMapping("/films")
    public void updateFilm(@RequestBody Film newFilm) {
     /*   if(films.containsKey(newFilm.getId())){
            films.put(newFilm.getId(),newFilm);
        }*/
    }

}
