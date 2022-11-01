package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Genre {

    @NotNull
    private int id;
    private String name;


    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
