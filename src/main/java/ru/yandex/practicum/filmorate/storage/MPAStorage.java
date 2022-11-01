package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.dao.MPADao;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MPAStorage {

    List<MPA> returnMPAs();

    MPA returnMPAById(int id);


}
