package com.example.movieticket.http;

import com.example.movieticket.models.LoginModel;
import com.example.movieticket.models.UserModel;
import com.example.movieticket.models.VerfiyToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/user/login")
    Call<ResponseHTTP<UserModel>> login(@Body LoginModel loginModel);

    @POST("/user/verify-token")
    Call<ResponseHTTP<UserModel>> verifyToken(@Body VerfiyToken verifyInfor);
}
