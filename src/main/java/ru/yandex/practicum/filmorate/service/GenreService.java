package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.impl.GenreDaoImpl;
import ru.yandex.practicum.filmorate.impl.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@Slf4j
public class GenreService {
    private GenreDaoImpl genreDao;

    @Autowired
    public GenreService(GenreDaoImpl genreDao) {
        this.genreDao = genreDao;
    }


    public Genre returnGenre(int id) {
        log.info("Service:method,genreService->returnGenre");
        return genreDao.returnGenreById(id);
    }

    public List<Genre> returnGenres() {
        log.info("Service:method,genreService->returnGenres");
        return genreDao.returnGenres();
    }




}
