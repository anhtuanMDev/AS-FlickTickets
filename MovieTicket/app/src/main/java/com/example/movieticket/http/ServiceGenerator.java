package com.example.movieticket.http;

import android.content.Context;

public class ServiceGenerator {

    public static UserService createUserService(Context context){
        return RetrofitClient.createRetrofit(context).create(UserService.class);
    }
}
