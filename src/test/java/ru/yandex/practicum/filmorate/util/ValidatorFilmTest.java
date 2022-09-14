package ru.yandex.practicum.filmorate.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorFilmTest {

    private Film film;
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        film = new Film(ValidatorFilm.generateId(), "Movie", "description", LocalDate.of(2000, 04
                , 04), 120);

        filmController = new FilmController();
        filmController.createFilm(film);
    }

    @AfterEach
    void tearDown() {
        ValidatorFilm.id = 0;
    }


    @Test
    void findFilmIsTrue() {
        film = new Film(1, "Movie", "description", LocalDate.of(2000, 04
                , 04), 120);
        assertEquals(true, ValidatorFilm.findFilm(film),"фильм есть в базе");


    }

    @Test
    void findFilmIsFalse() {
        film = new Film(3, "Movie", "description", LocalDate.of(2000, 04
                , 04), 120);
        assertEquals(false, ValidatorFilm.findFilm(film),"фильма нет в базе");

    }

    @Test
    void checkIdForUpdate() {
        film = new Film(-12, "Movie", "description", LocalDate.of(2000, 04
                , 04), 120);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> ValidatorFilm.checkIdForUpdate(film));
        assertEquals("incorrect Id for update film", exception.getMessage());
    }

    @Test
    void checkIdWithException() {
        film = new Film(1, "Movie", "description", LocalDate.of(2000, 04
                , 04), 120);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> ValidatorFilm.checkId(film));
        assertEquals("Фильм с id " + film.getId() + " уже существует в БД" +
                ", надо использовать другой метод", exception.getMessage());

    }

    @Test
    void checkIdWithSetNewId() {
        film = new Film(0, "Movie", "description", LocalDate.of(2000, 04
                , 04), 120);

       ValidatorFilm.checkId(film);
       assertEquals(2,film.getId(),"не совпало значение по id");

    }

    @Test
    void checkReleaseDate() {
        film = new Film(ValidatorFilm.generateId(), "Movie", "description"
                , LocalDate.of(1800, 04, 04), 120);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> ValidatorFilm.checkReleaseDate(film));
        assertEquals("incorrect ReleaseDate", exception.getMessage());

    }

    @Test
    void generateId() {
        film = new Film(ValidatorFilm.generateId(), "Movie", "description"
                , LocalDate.of(1800, 04, 04), 120);
        assertEquals(2,film.getId(),"не совпало значение по id");

    }
}