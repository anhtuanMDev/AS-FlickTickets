package com.example.movieticket.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHTTPInstance {
    private static OkHttpClient instance;

    public static OkHttpClient getInstance(){
        if (instance == null) {
            // Create a logging interceptor for debugging
            // Build the OkHttpClient with custom settings
            instance = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Set the connection timeout
                    .readTimeout(30, TimeUnit.SECONDS) // Set the read timeout
                    .writeTimeout(30, TimeUnit.SECONDS) // Set the write timeout
                    .build();
        }
        return instance;
    }
}
