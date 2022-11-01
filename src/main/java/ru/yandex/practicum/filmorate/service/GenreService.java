package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.impl.GenreDaoImpl;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.util.List;

@Service
@Slf4j
public class GenreService {

    private final GenresStorage genresStorage;

    @Autowired
    public GenreService(GenresStorage genresStorage) {
        this.genresStorage = genresStorage;
    }


    public Genre returnGenre(int id) {
        log.info("Service:method,genreService->returnGenre");
        return genresStorage.returnGenreById(id);
    }

    public List<Genre> returnGenres() {
        log.info("Service:method,genreService->returnGenres");
        return genresStorage.returnGenres();
    }




}
