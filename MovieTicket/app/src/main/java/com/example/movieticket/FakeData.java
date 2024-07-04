package com.example.movieticket;

import com.example.movieticket.models.DisplayMovie;

import java.util.List;

public class FakeData {
    static String poster = "https://m.media-amazon.com/images/I/71niXI3lxlL._AC_SY679_.jpg";
    public static List<DisplayMovie> recommend = List.of(
            new DisplayMovie("Spiderman", poster, 9, "1", "Action, Adventure, Fantasy"),
            new DisplayMovie("Spiderman", poster, 9, "1", "Action, Adventure, Fantasy"),
            new DisplayMovie("Spiderman", poster, 9, "1", "Action, Adventure, Fantasy"),
            new DisplayMovie("Spiderman", poster, 9, "1", "Action, Adventure, Fantasy"),
            new DisplayMovie("Spiderman", poster, 9, "1", "Action, Adventure, Fantasy"),
            new DisplayMovie("Spiderman", poster, 9, "1", "Action, Adventure, Fantasy"));
}
