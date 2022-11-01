package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j

public class GenresController {

    private final GenreService genreService;

    public GenresController(GenreService genreService) {
        this.genreService = genreService;
    }


    @GetMapping()
    public List<Genre> returnGenres() {
        log.info("controller:method genresController -> returnGenres");
        return genreService.returnGenres();
    }

    //get user
    @GetMapping("/{id}")
    public Genre returnGenre(@PathVariable int id) {
        log.info("controller:method genresController -> returnGenre");
        return genreService.returnGenre(id);
    }



}
