package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.impl.FilmDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class FilmService {
    private FilmDaoImpl filmDao;

    @Autowired
    public FilmService(FilmDaoImpl filmDao) {
        this.filmDao = filmDao;
    }

    public void addLike(int idFilm, int userId) {
        log.info("Service:method,filmService->addLike");
        filmDao.addLike(idFilm, userId);
    }

    public void deleteLike(int id, int userId) {
        log.info("Service:method,filmService->deleteLike");
        filmDao.deleteLike(id, userId);
    }

    public List<Film> returnTopFilms(int count) {
        log.info("Service:method,filmService->returnTopFilms");
        return filmDao.returnTopFilms(count);
    }

    public Film addFilm(Film film) {
        log.info("Service:method,filmService->addFilm");
        filmDao.addFilm(film);
        return film;
    }

    public List<Film> returnFilms() {
        log.info("Service:method,filmService->returnFilms");
        return filmDao.returnFilms();
    }

    public Film returnFilmById(int id) {
        log.info("Service:method,filmService->returnFilmById");
        return filmDao.returnFilmById(id);
    }

    public Film updateFilm(Film film) {
        log.info("Service:method,filmService->updateFilm");
        filmDao.updateFilm(film);
        return film;
    }

//    public Integer generateId() {
//        return filmDao.generateId();
//    }


}
