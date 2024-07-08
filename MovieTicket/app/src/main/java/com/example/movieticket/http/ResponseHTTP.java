package com.example.movieticket.http;

public class ResponseHTTP<T> {
    private T data;
    private String message;
    private boolean status;

    public T getBody() {
        return data;
    }

    public void setBody(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
