package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class FilmService {

    private InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.inMemoryFilmStorage = (InMemoryFilmStorage) filmStorage;
    }

    public void addLike(int idFilm, int userId) {
        log.info("Service:method,filmService->addLike");
        inMemoryFilmStorage.addLike(idFilm, userId);
    }

    public void deleteLike(int id, int userId) {
        log.info("Service:method,filmService->deleteLike");
        inMemoryFilmStorage.deleteLike(id, userId);
    }

    public List<Film> returnTopFilms(int count) {
        log.info("Service:method,filmService->returnTopFilms");
        return inMemoryFilmStorage.returnTopFilms(count);
    }

    public Film addFilm(Film film) {
        log.info("Service:method,filmService->addFilm");
        inMemoryFilmStorage.addFilm(film);
        return film;
    }

    public ArrayList<Film> returnFilms() {
        log.info("Service:method,filmService->returnFilms");
        return new ArrayList<>(inMemoryFilmStorage.getFilms().values());
    }

    public Film returnFilmById(int id) {
        log.info("Service:method,filmService->returnFilmById");
        return inMemoryFilmStorage.returnFilmById(id);
    }

    public Film updateFilm(Film film) {
        log.info("Service:method,filmService->updateFilm");
        inMemoryFilmStorage.updateFilm(film);
        return film;
    }

    public Integer generateId() {
        return inMemoryFilmStorage.generateId();
    }


}
