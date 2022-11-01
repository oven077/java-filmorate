package ru.yandex.practicum.filmorate.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@Setter
@Getter
@Slf4j

public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Genre> returnGenres() {
        return jdbcTemplate.query("SELECT * FROM GENRES", new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Genre returnGenreById(int id) {
        checkId(id);

        return jdbcTemplate.query("SELECT * FROM GENRES WHERE id = ?", new BeanPropertyRowMapper<>(Genre.class), id)
                .stream()
                .findAny().orElse(null);

    }


//    public void load(List<Film> films) {
//        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
//        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));
//        final String sqlQuery = "select * from genres g, film_genres fg where fg.genre_id = g.id and fg.film_id " +
//                "in (" + inSql + ")";
//        jdbcTemplate.query(sqlQuery, (rs) -> {
//            final Film film = filmById.get(rs.getInt("FILM_ID"));
//            film.addGenre(makeGenre(rs, 0));
//        }, films.stream().map(Film::getId).toArray());
//    }
//
//
//    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
//        return new Genre(
//                rs.getInt("id"),
//                rs.getString("name"));
//    }


    public static void checkId(int id) {
        if (id <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new BadRequestException("incorrect id for update user");
        }
    }
}
