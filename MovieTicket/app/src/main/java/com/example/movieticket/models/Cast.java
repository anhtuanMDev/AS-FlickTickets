package com.example.movieticket.models;

public class Cast {
    private String realName;
    private String realImage;
    private String nameInMovie;
    private String imageInMovie;

    public Cast(String realName, String realImage, String nameInMovie, String imageInMovie) {
        this.realName = realName;
        this.realImage = realImage;
        this.nameInMovie = nameInMovie;
        this.imageInMovie = imageInMovie;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealImage() {
        return realImage;
    }

    public void setRealImage(String realImage) {
        this.realImage = realImage;
    }

    public String getNameInMovie() {
        return nameInMovie;
    }

    public void setNameInMovie(String nameInMovie) {
        this.nameInMovie = nameInMovie;
    }

    public String getImageInMovie() {
        return imageInMovie;
    }

    public void setImageInMovie(String imageInMovie) {
        this.imageInMovie = imageInMovie;
    }
}
