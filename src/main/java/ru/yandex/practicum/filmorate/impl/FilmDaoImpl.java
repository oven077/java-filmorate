package ru.yandex.practicum.filmorate.impl;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.util.ValidatorFilm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Setter
@Getter
@Slf4j
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;
    private final GenreDaoImpl genreDao;


    public FilmDaoImpl(JdbcTemplate jdbcTemplate, UserService userService, GenreDaoImpl genreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
        this.genreDao = genreDao;
    }


    @Override
    public Film addFilm(Film film) {
        ValidatorFilm.globalCheck(film);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        final String sql = "INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, RATE, DURATION, MPA_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getRate());
            ps.setInt(5, film.getDuration());
            ps.setInt(6, film.getMpa().getId());

            return ps;
        }, keyHolder);

        film.setId(keyHolder.getKey().intValue());

        saveGenres(film);

        return film;

    }

    private void saveGenres(Film film) {

        jdbcTemplate.update("delete from FILM_GENRES where FILM_ID = ?", film.getId());
        final Set<Genre> genres = film.getGenres();

        if (genres == null || genres.isEmpty()) {
            return;
        }


        final ArrayList<Genre> genreList = new ArrayList<>(genres);

        jdbcTemplate.batchUpdate("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) values ( ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, film.getId());
                        ps.setInt(2, genreList.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return genreList.size();
                    }
                });
    }


    @Override
    public List<Film> returnFilms() {

        List<Film> filmList = jdbcTemplate.query("SELECT * FROM FILMS AS f,MPA AS m WHERE f.MPA_ID = m.ID"
                , FilmDaoImpl::makeFilm);


        for (Film film : filmList) {
            List<Genre> genreList = new ArrayList<>();
            genreList = jdbcTemplate.query("SELECT * FROM FILM_GENRES AS fg Left Join GENRES G2 on G2.ID = fg.GENRE_ID WHERE fg.FILM_ID = ?"
                    , new BeanPropertyRowMapper<>(Genre.class), film.getId());
            Set<Genre> genres = new HashSet<>();
            for (Genre genre : genreList) {
                genres.add(genre);
            }
            film.setGenres(genres);

        }
        return filmList;
    }

    static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {

        return (new Film(
                rs.getInt("id"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getDate("Release_Date").toLocalDate(),
                rs.getInt("Duration"),
                new MPA(rs.getInt("MPA.id"), rs.getString("MPA.name")),
                rs.getInt("Rate"),
                null

        ));
    }


    @Override
    public Film updateFilm(Film film) {
        if (returnFilmById(film.getId()) != null) {
            jdbcTemplate.update("update FILMS set NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, RATE = ?" +
                            ", DURATION = ?, MPA_ID = ? WHERE ID = ?"
                    , film.getName(), film.getDescription(), film.getReleaseDate(), film.getRate(), film.getDuration()
                    , film.getMpa().getId(),film.getId());
        } else {
            throw new BadRequestException("incorrect id for update user");
        }

        saveGenres(film);


        List<Genre> genreList = new ArrayList<>();
        genreList = jdbcTemplate.query("SELECT * FROM FILM_GENRES AS fg Left Join GENRES G2 on G2.ID = fg.GENRE_ID WHERE fg.FILM_ID = ?"
                , new BeanPropertyRowMapper<>(Genre.class), film.getId());
        Set<Genre> genres = new HashSet<>();
        for (Genre genre : genreList) {
            genres.add(genre);

        }
        film.setGenres(genres);



        return film;
    }

    @Override
    public Film returnFilmById(int id) {


        Film film = new Film();


        checkId(id);

        if (jdbcTemplate.query("SELECT * FROM FILMS AS f,MPA AS m WHERE f.MPA_ID = m.ID AND f.id = ?", FilmDaoImpl::makeFilm, id)
                .stream()
                .findAny().orElse(null) != null) {
            film = jdbcTemplate.query("SELECT * FROM FILMS AS f,MPA AS m WHERE f.MPA_ID = m.ID AND f.id = ?", FilmDaoImpl::makeFilm, id)
                    .stream()
                    .findAny().orElse(null);

        } else {
            throw new BadRequestException("Фильм с id " + id + " не найден");
        }

        List<Genre> genreList = new ArrayList<>();
        genreList = jdbcTemplate.query("SELECT * FROM FILM_GENRES AS fg Left Join GENRES G2 on G2.ID = fg.GENRE_ID WHERE fg.FILM_ID = ?"
                , new BeanPropertyRowMapper<>(Genre.class), film.getId());
        Set<Genre> genres = new HashSet<>();
        for (Genre genre : genreList) {
            genres.add(genre);

        }
        film.setGenres(genres);

        return film;


    }


    @Override
    public List<Film> returnTopFilms(int count) {


        return jdbcTemplate.query("SELECT\n" +
                "    f.NAME,\n" +
                "    f.RATE,\n" +
                "    f.DESCRIPTION,\n" +
                "    f.DURATION,\n" +
                "    f.RELEASE_DATE,\n" +
                "    f.MPA_ID,\n" +
                "    f.ID,\n" +
                "    MPA.NAME,\n" +
                "    MPA.ID,\n" +
                "        count( LIKES.USER_ID) USER_ID\n" +
                "FROM FILMS AS F LEFT JOIN LIKES on LIKES.FILM_ID = F.ID\n" +
                "INNER JOIN MPA on MPA.ID = f.MPA_ID\n" +
                "group by f.NAME, f.RATE, f.DESCRIPTION, f.DURATION, f.RELEASE_DATE, f.MPA_ID, f.ID, MPA.NAME, MPA_ID\n" +
                "ORDER BY USER_ID DESC, f.ID DESC\n" +
                "LIMIT ?", FilmDaoImpl::makeFilm, count);
    }

    @Override
    public void deleteLike(int id, int userId) {
        checkId(id);
        checkId(userId);

        if (returnFilmById(id) != null && userService.returnUserById(userId) != null) {
            jdbcTemplate.update("delete from LIKES where user_id = ? AND FILM_ID = ?", userId, id);

        } else {
            throw new BadRequestException("Фильм с id " + id + " не найден");
        }
    }

    @Override
    public void addLike(int filmId, int userId) {
        checkId(filmId);
        checkId(userId);


        if (returnFilmById(filmId) != null && userService.returnUserById(userId) != null) {


            final String sql = "INSERT INTO LIKES (USER_ID, FILM_ID) values ( ?, ?)";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, filmId);
                return ps;
            });

        } else {
            throw new BadRequestException("Фильм с id " + filmId + " не найден");

        }

    }

    public static void checkId(int id) {
        if (id <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new BadRequestException("incorrect id for update user");
        }
    }


}
