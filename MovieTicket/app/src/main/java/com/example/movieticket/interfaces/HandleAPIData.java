package com.example.movieticket.interfaces;

public interface HandleAPIData<T> {
    void onCallComplete(T data);
}
