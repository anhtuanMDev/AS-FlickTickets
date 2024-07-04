package com.example.movieticket.models;

public class MinimumMovie {
    private String title;
    private String poster;
    private String id;

    public MinimumMovie(String title, String poster, String id) {
        this.title = title;
        this.poster = poster;
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

}
