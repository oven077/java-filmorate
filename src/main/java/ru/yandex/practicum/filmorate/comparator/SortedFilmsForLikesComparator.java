package ru.yandex.practicum.filmorate.comparator;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class SortedFilmsForLikesComparator implements Comparator<Film> {
    public int compare(Film film1, Film film2) {


        int size1 = film1.getFilmLikes().size();
        int size2 = film2.getFilmLikes().size();


        if (size1 > size2) {
            return +1;
        } else if (size1 < size2) {
            return -1;
        } else {
            return 0;
        }


    }
}
