package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.CustomLog;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
@Data
@RequiredArgsConstructor
public class Film {
    @NotBlank
    private String name;
    @NotNull
    @Size(min = 1, max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @NotNull
    @Min(value = 0)
    private int duration;
    @NotNull
    private int id;
    private MPA mpa;
    private int rate;

    private Set<Integer> filmLikes = new HashSet<>();

    private Set<Genre> genres = new HashSet<>();


    public Film(int id,String name, String description, LocalDate releaseDate, int duration, MPA mpa, int rate
            , Set<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.id = id;
        this.mpa = mpa;
        this.rate = rate;
        this.genres = genres;
    }


    public void addGenre(Genre genre){
        genres.add(genre);
    }


}
