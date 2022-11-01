package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class MPA {


    @NotNull
    private int id;
    private String name;


    public MPA(int id, String name) {
        this.id = id;
        this.name = name;

    }
}
