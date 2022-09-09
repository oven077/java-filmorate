package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Film {

    private String name;
    private int id;
    private String description;
    private LocalDate releaseDate;
    private int duration;

}
