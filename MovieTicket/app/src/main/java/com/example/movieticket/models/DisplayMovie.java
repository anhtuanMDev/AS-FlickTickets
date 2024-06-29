package com.example.movieticket.models;

public class DisplayMovie {
    private String title;
    private String poster;
    private int rate;
    private String id;

    public DisplayMovie(String title, String poster, int rate, String id) {
        this.title = title;
        this.poster = poster;
        this.rate = rate;
        this.id = id;
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
