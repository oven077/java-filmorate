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
        inMemoryFilmStorage.addLike(idFilm, userId);
    }

    public void deleteLike(int id, int userId) {
        inMemoryFilmStorage.deleteLike(id, userId);
    }

    public List<Film> returnTopFilms(int count) {
        return inMemoryFilmStorage.returnTopFilms(count);
    }

    public Film addFilm(Film film) {
        inMemoryFilmStorage.addFilm(film);
        return film;
    }

    public ArrayList<Film> returnFilms() {
        return new ArrayList<>(inMemoryFilmStorage.getFilms().values());
    }

    public Film returnFilmById(int id) {
        return inMemoryFilmStorage.returnFilmById(id);
    }

    public Film updateFilm(Film film) {
        inMemoryFilmStorage.updateFilm(film);
        return film;
    }

    public Integer generateId() {
        return inMemoryFilmStorage.generateId();
    }


}
