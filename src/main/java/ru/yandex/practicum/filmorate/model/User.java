package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class User {

    @NotNull
    private int id;
    private LocalDate birthday;

    @NotBlank
    private String email;
    private String login;
    private String name;

    private Set<Integer> friends = new HashSet<>();


    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.birthday = birthday;
        this.email = email;
        this.login = login;
        this.name = name;
    }
}
