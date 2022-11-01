/*
package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.impl.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmServiceTest {

    private Film film;
    private User user;
    private FilmService filmService;
    private UserService userService;
    private InMemoryFilmStorage inMemoryFilmStorage;
    private InMemoryUserStorage inMemoryUserStorage;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {


        //inMemoryUserStorage = new InMemoryUserStorage();

        jdbcTemplate = new JdbcTemplate();


        UserDaoImpl userDaoImpl = new UserDaoImpl(jdbcTemplate);

        userService = new UserService(userDaoImpl);

        inMemoryFilmStorage = new InMemoryFilmStorage(userService);
        filmService = new FilmService(inMemoryFilmStorage);



        film = new Film(1, "Movie1", "description", LocalDate.of(2000, 04
                , 04), 120);
        user = new User(1, "asd@asd.ru", "login", "name1"
                , LocalDate.of(1982, 04, 07));


        userService.addUser(user);
        filmService.addFilm(film);

    }

    @AfterEach
    void tearDown() {
        inMemoryUserStorage.returnUsers().clear();
        inMemoryFilmStorage.returnFilms().clear();
    }

    @Test
    void addLike() {

        filmService.addLike(film.getId(), user.getId());
        assertEquals(1, filmService.returnFilmById(film.getId()).getFilmLikes().size()
                , "user нет в базе");
    }

    @Test
    void deleteLike() {
        filmService.addLike(film.getId(), user.getId());
        filmService.deleteLike(film.getId(), user.getId());

        assertEquals(0, filmService.returnFilmById(film.getId()).getFilmLikes().size()
                , "user нет в базе");
    }

    @Test
    void returnTopTwoFilmsAndLikes() {

        userService.addUser(new User(2, "asd@asd.ru", "login", "name2"
                , LocalDate.of(1982, 04, 07)));
        filmService.addFilm(new Film(2, "Movie2", "description", LocalDate.of(2000, 04
                , 04), 120));

        userService.addUser(new User(3, "asd@asd.ru", "login", "name3"
                , LocalDate.of(1982, 04, 07)));
        filmService.addFilm(new Film(3, "Movie3", "description", LocalDate.of(2000, 04
                , 04), 120));

        userService.addUser(new User(4, "asd@asd.ru", "login", "name4"
                , LocalDate.of(1982, 04, 07)));
        filmService.addFilm(new Film(4, "Movie4", "description", LocalDate.of(2000, 04
                , 04), 120));


        filmService.addLike(2, 2);
        filmService.addLike(2, 3);
        filmService.addLike(2, 4);

        filmService.addLike(1, 2);
        filmService.addLike(1, 3);

        assertEquals(2, filmService.returnTopFilms(2).size(), "Не совпадает количество топовых фильмов");


        assertEquals(5, filmService.returnTopFilms(2).stream()
                .map(x -> x.getFilmLikes().size())
                .mapToInt(i -> i.intValue()).sum(), "не совпадает количество лайков");

    }

    @Test
    void addFilm() {
        filmService.addFilm(new Film(4, "Movie4", "description", LocalDate.of(2000, 04
                , 04), 120));
        assertEquals(2, filmService.returnFilms().size(), "Не совпадает количество фильмов при добавлении");
    }

    @Test
    void returnFilms() {
        assertEquals(1, filmService.returnFilms().size(), "Неверное количество фильмов при получении");
    }

    @Test
    void returnFilmById() {
        assertEquals(film, filmService.returnFilmById(1), "Не совпадают фильмы при запросе по id");
    }

    @Test
    void updateFilm() {

        film.setId(3);
        assertEquals(3, filmService.updateFilm(film).getId(), "Не совпадает id после обновления");

    }


}
*/
