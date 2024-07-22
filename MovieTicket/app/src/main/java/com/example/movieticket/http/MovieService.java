package com.example.movieticket.http;

import com.example.movieticket.models.DisplayMovie;
import com.example.movieticket.models.MinimumMovie;
import com.example.movieticket.models.MovieDetail;
import com.example.movieticket.models.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("movie/list/{page}")
    Call<ResponseHTTP<MovieModel>> getMovies(@Path("page") int page);

    @GET("movie/recommend")
    Call<ResponseHTTP<List<DisplayMovie>>> getRecommend(@Query("page") int page);

    @GET("movie/display")
    Call<ResponseHTTP<List<MinimumMovie>>> getDisplay(@Query("page") int page);

    @GET("movie/detail/{id}/{userId}")
    Call<ResponseHTTP<MovieDetail>> getMovieDetail(@Path("id") int id, @Path("userId") int userId);

}
