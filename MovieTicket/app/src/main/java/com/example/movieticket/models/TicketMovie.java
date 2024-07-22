package com.example.movieticket.models;

public class TicketMovie {
    private String title;
    private String poster;
    private String tags;
    private String release_date;
    private int id;
    private int duration;
    private int rate;

    public TicketMovie(String title, String poster, String tags, String release_date, int id, int duration, int rate) {
        this.title = title;
        this.poster = poster;
        this.tags = tags;
        this.release_date = release_date;
        this.id = id;
        this.duration = duration;
        this.rate = rate;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
