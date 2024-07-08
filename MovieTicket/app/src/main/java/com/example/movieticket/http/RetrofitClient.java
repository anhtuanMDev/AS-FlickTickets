package com.example.movieticket.http;

import android.content.Context;

import com.example.movieticket.models.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = Constant.baseUrl;

    public static Retrofit createRetrofit(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHTTPInstance.getInstance())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
