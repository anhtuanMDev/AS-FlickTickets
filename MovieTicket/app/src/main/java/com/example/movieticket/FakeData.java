package com.example.movieticket;

import com.example.movieticket.models.DisplayMovie;

import java.util.List;

public class FakeData {
    static String spiderman = "https://i.ebayimg.com/images/g/TEEAAOSwnK9ZV6VQ/s-l960.jpg";
    static String ironman = "https://m.media-amazon.com/images/I/61cSBq+psoL._SY741_.jpg";
    static String theDarkKnight = "https://m.media-amazon.com/images/I/818hyvdVfvL._AC_UF894,1000_QL80_.jpg";
    static String inception = "https://m.media-amazon.com/images/I/61cN-XN94TL._AC_UF894,1000_QL80_.jpg";
    static String interstellar = "https://upload.wikimedia.org/wikipedia/vi/4/46/Interstellar_poster.jpg";
    static String endgame = "https://m.media-amazon.com/images/I/71niXI3lxlL._AC_SY679_.jpg";
    static String matrix = "https://i.ebayimg.com/images/g/zwAAAOSwCGVX5wN4/s-l1200.jpg";
    static String avatar = "https://i.ebayimg.com/images/g/CwEAAOSwv4xf5cdv/s-l1200.jpg";
    static String jurassic = "https://vice-press.com/cdn/shop/products/Jurassic-Park-Editions-poster-florey.jpg?v=1654518755&width=1024";
    static String gladiator = "https://m.media-amazon.com/images/I/51cV7a82q+L._AC_UF894,1000_QL80_.jpg";
    public static List<DisplayMovie> recommend = List.of(
            new DisplayMovie("Spider man", spiderman, 90, "1", "Action, Adventure, Fantasy"),
            new DisplayMovie("Iron Man", ironman, 80, "1", "Action, Sci-Fi"),
            new DisplayMovie("The Dark Knight", theDarkKnight, 93, "1", "Action, Crime, Drama"),
            new DisplayMovie("Inception", inception, 88, "1", "Action, Adventure, Sci-Fi, American"),
            new DisplayMovie("Interstellar", interstellar, 86, "1", "Adventure, Drama, Sci-Fi, Space"),
            new DisplayMovie("Avengers: Endgame", endgame, 84, "1", "Action, Adventure, Drama"),
            new DisplayMovie("The Matrix", matrix, 87, "1", "Action, Sci-Fi"),
            new DisplayMovie("Avatar", avatar, 78, "1", "Action, Adventure, Fantasy"),
            new DisplayMovie("Jurassic Park", jurassic, 81, "1", "Action, Adventure, Sci-Fi"),
            new DisplayMovie("Gladiator", gladiator, 85, "1", "Action, Drama")
    );
}
