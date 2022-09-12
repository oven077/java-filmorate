package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {

    private int id;
    @NotBlank
    private String name;
    @NotNull
    @Max(value = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @NotNull
    @Min(value = 0)
    private int duration;

}
