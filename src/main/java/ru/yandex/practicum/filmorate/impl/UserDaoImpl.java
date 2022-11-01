package ru.yandex.practicum.filmorate.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@Setter
@Getter
@Slf4j
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        final String sql = "INSERT INTO users (email, login, name, birthday) VALUES(?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());

        return user;

    }

    @Override
    public List<User> returnUsers() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User updateUser(User user) {

        checkUserId(user);
        if (returnUserById(user.getId()) != null) {
            jdbcTemplate.update("update users set EMAIL = ?, login = ?, NAME = ?, BIRTHDAY = ? ", user.getEmail()
                    , user.getLogin(), user.getName(), user.getBirthday());
        } else {
            throw new BadRequestException("incorrect id for update user");
        }
        return user;
    }


    public static void checkUserId(User user) {
        if (user.getId() <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new BadRequestException("incorrect id for update user");
        }
    }


    @Override
    public User returnUserById(int id) {
        checkId(id);


        if(jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new BeanPropertyRowMapper<>(User.class), id)
                .stream()
                .findAny().orElse(null) != null){
            return jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new BeanPropertyRowMapper<>(User.class), id)
                    .stream()
                    .findAny().orElse(null);

        } else {
            throw new BadRequestException("Пользователь с id " + id + " не найден");
        }
    }

    @Override
    public void addFriend(int id, int friendId) {
        checkId(id);
        checkId(friendId);
        if (returnUserById(id) != null && returnUserById(friendId) != null) {
            jdbcTemplate.update("INSERT INTO friends VALUES(?, ?)", id, friendId);
        } else {
            throw new BadRequestException("Пользователь с id " + id + " не найден");
        }


    }

    @Override
    public void deleteFromFriendList(int id, int userId) {
        checkId(id);
        checkId(userId);
        if (returnUserById(id) != null) {
            jdbcTemplate.update("DELETE FROM FRIENDS WHERE FRIEND_ID = ?", userId);
        } else {
            throw new BadRequestException("Пользователь с id " + id + " не найден");
        }
    }

    @Override
    public List<User> returnCommonFriends(int id, int otherId) {

        checkId(id);
        checkId(otherId);

        if (returnUserById(id) != null) {
            return jdbcTemplate.query("SELECT DISTINCT\n" +
                    "    u.ID,\n" +
                    "    u.EMAIL,\n" +
                    "    u.LOGIN,\n" +
                    "    u.NAME,\n" +
                    "    u.BIRTHDAY\n" +
                    "    FROM USERS AS u, FRIENDS f,FRIENDS o\n" +
                    "WHERE u.ID = f.FRIEND_ID AND u.ID = o.FRIEND_ID AND f.USER_ID = ? AND o.USER_ID = ?" +
                    ";", new BeanPropertyRowMapper<>(User.class), id, otherId);
        } else {
            throw new BadRequestException("Пользователь с id " + id + " не найден");
        }


    }

    @Override
    public List<User> returnFriendsUser(int id) {
        checkId(id);
        if (returnUserById(id) != null) {
            return jdbcTemplate.query("SELECT DISTINCT id, email, login, name, BIRTHDAY FROM users AS u " +
                    "INNER JOIN friends AS f" +
                    " ON u.id = f.FRIEND_ID" +
                    " WHERE f.USER_ID = ?", new BeanPropertyRowMapper<>(User.class), id);
        } else {
            throw new BadRequestException("Пользователь с id " + id + " не найден");
        }
    }

    public static void checkId(int id) {
        if (id <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new BadRequestException("incorrect id for update user");
        }
    }


}
