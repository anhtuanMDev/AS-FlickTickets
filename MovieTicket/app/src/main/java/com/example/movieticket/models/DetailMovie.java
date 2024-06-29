package com.example.movieticket.models;

public class DetailMovie {
    private String id;
    private String title;
    private String descr;
    private String poster;
    private String[] tags;
    private String release_date;
    private String duration;
    private String trailer;
    private int rate;
    private Cast[] actor;
    private boolean favorite;
    private String banner;

    public DetailMovie(String id, String title, String descr, String poster, String[] tags, String release_date, String duration, String trailer, int rate, Cast[] actor, boolean favorite, String banner) {
        this.id = id;
        this.title = title;
        this.descr = descr;
        this.poster = poster;
        this.tags = tags;
        this.release_date = release_date;
        this.duration = duration;
        this.trailer = trailer;
        this.rate = rate;
        this.actor = actor;
        this.favorite = favorite;
        this.banner = banner;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Cast[] getActor() {
        return actor;
    }

    public void setActor(Cast[] actor) {
        this.actor = actor;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
