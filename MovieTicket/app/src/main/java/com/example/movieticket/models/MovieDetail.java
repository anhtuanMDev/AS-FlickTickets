package com.example.movieticket.models;

import java.util.List;

public class MovieDetail {
    private int id;
    private String title;
    private String descr;
    private String poster;
    private String releaseDate;
    private int duration;
    private int rate;
    private String trailer;
    private String banner;
    private String genres;
    private List<Cast> actors;
    private boolean isFavorite;
    private List<ReviewModel> reviews;
    private List<String> showtimes;
    private List<Integer> availableSeats;

    // Constructor
    public MovieDetail(int id, String title, String descr, String poster, String releaseDate,
                       int duration, int rate, String trailer, String banner, String genres,
                       List<Cast> actors, boolean isFavorite, List<ReviewModel> reviews,
                       List<String> showtimes, List<Integer> availableSeats) {
        this.id = id;
        this.title = title;
        this.descr = descr;
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.trailer = trailer;
        this.banner = banner;
        this.genres = genres;
        this.actors = actors;
        this.isFavorite = isFavorite;
        this.reviews = reviews;
        this.showtimes = showtimes;
        this.availableSeats = availableSeats;
    }

    // Getters and Setters (omitted for brevity, you can generate these using IDE)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public List<Cast> getActors() {
        return actors;
    }

    public void setActors(List<Cast> actors) {
        this.actors = actors;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }

    public List<String> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<String> showtimes) {
        this.showtimes = showtimes;
    }

    public List<Integer> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Integer> availableSeats) {
        this.availableSeats = availableSeats;
    }
}
