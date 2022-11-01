package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.comparator.SortedFilmsForLikesComparator;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.util.ValidatorFilm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Setter
@Getter
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final UserService userService;
    public HashMap<Integer, Film> films = new HashMap<>();
    private static int id = 0;

    @Autowired
    public InMemoryFilmStorage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Film addFilm(Film film) {
        ValidatorFilm.globalCheck(film);

        if (film.getId() > 0) {
            if (findFilm(film)) {
                log.info("фильм уже есть в базе");
                throw new ValidationException("Фильм с id " + film.getId() + " уже существует в БД" +
                        ", надо использовать другой метод");
            }
        } else if (film.getId() == 0) {
            ValidatorFilm.globalCheck(film);
            film.setId(generateId());
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public ArrayList<Film> returnFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film updateFilm(Film film) {
        checkFilm(film);

        if (findFilm(film)) {
            films.put(film.getId(), film);

        } else {
            return addFilm(film);
        }
        return film;
    }

    @Override
    public Film returnFilmById(int id) {
        checkId(id);
        if (films.containsKey(id)) {
            return films.get(id);
        }
        return null;
    }

    @Override
    public List<Film> returnTopFilms(int count) {
        return getFilms().values().stream()
                .sorted(new SortedFilmsForLikesComparator().reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLike(int id, int userId) {
        checkId(id);

        if (returnFilmById(id) != null) {
            returnFilmById(id)
                    .getFilmLikes()
                    .remove(userId);
        } else {
            throw new BadRequestException("Фильм с id " + id + " не найден");
        }
    }

    @Override
    public void addLike(int filmId, int userId) {
        checkId(filmId);

        if (returnFilmById(filmId) != null) {
            returnFilmById(filmId)
                    .getFilmLikes()
                    .add(userId);
        } else {
            throw new BadRequestException("Фильм с id " + filmId + " не найден");
        }
    }

    public boolean findFilm(Film film) {
        if (this.films.containsKey(film.getId())) {
            System.out.println("Найден фильм " + film);
            return true;
        }
        return false;
    }

    public static void checkFilm(Film film) {
        if (film.getId() <= 0) {
            log.info("При обновлении фильма пустой id");
            throw new BadRequestException("incorrect Id for update film");
        }
    }

    public static void checkId(int id) {
        if (id <= 0) {
            log.info("При обновлении фильма пустой id");
            throw new BadRequestException("incorrect Id for update film");
        }
    }

    public int generateId() {
        id++;
        return id;
    }

    public HashMap<Integer, Film> getFilms() {
        return films;
    }
}
