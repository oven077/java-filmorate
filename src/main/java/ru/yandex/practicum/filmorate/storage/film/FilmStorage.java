package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    ArrayList<Film> returnFilms();

    Film updateFilm(Film film);

    Film returnFilmById(int id);

    public List<Film> returnTopFilms(int count);

    public void deleteLike(int id, int userId);

    public void addLike(int id, int userId);



}
