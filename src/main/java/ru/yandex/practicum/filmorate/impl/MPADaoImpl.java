package ru.yandex.practicum.filmorate.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MPADao;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@Component
@Setter
@Getter
@Slf4j

public class MPADaoImpl implements MPADao {


    private final JdbcTemplate jdbcTemplate;

    public MPADaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<MPA> returnMPAs() {
        return jdbcTemplate.query("SELECT * FROM MPA", new BeanPropertyRowMapper<>(MPA.class));
    }

    @Override
    public MPA returnMPAById(int id) {
        return jdbcTemplate.query("SELECT * FROM MPA WHERE id = ?", new BeanPropertyRowMapper<>(MPA.class), id)
                .stream()
                .findAny().orElse(null);
    }
}
