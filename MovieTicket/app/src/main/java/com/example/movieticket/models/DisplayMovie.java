package com.example.movieticket.models;

public class DisplayMovie {
    private String id;
    private String title;
    private String poster;
    private int rate;
    private String genres;

    public DisplayMovie(String title, String poster, int rate, String id, String genres) {
        this.title = title;
        this.poster = poster;
        this.rate = rate;
        this.id = id;
        this.genres = genres;
    }

    public String getTags() {
        return genres;
    }

    public void setTags(String genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String title) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
