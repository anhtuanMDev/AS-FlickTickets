package com.example.movieticket.models;

public class TicketMovie {
    private String title;
    private String poster;
    private String tags;
    private String release;
    private String id;
    private String duration;

    public TicketMovie(String title, String poster, String tags, String release, String id, String duration) {
        this.title = title;
        this.poster = poster;
        this.tags = tags;
        this.release = release;
        this.id = id;
        this.duration = duration;
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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
