package com.example.movieticket.http;

import com.example.movieticket.models.FavoriteModel;
import com.example.movieticket.models.LoginModel;
import com.example.movieticket.models.TicketMovie;
import com.example.movieticket.models.UserModel;
import com.example.movieticket.models.VerfiyToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("/user/login")
    Call<ResponseHTTP<UserModel>> login(@Body LoginModel loginModel);

    @POST("/user/verify-token")
    Call<ResponseHTTP<UserModel>> verifyToken(@Body VerfiyToken verifyInfor);

    @POST("/user/register")
    Call<ResponseHTTP<UserModel>> register(@Body UserModel userModel);

    @POST("user/favorite/create")
    Call<ResponseHTTP2> createFavorite(@Body FavoriteModel favoriteModel);

    @DELETE("user/favorite/remove/{userId}/{movieId}")
    Call<ResponseHTTP2> deleteFavorite(@Path("userId") int userId, @Path("movieId") int movieId);

    @GET("user/favorite/list/{userId}")
    Call<ResponseHTTP<List<TicketMovie>>> getFavorite(@Path("userId") int userId);

}
