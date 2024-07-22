package com.example.movieticket.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.movieticket.activities.LoginActivity;
import com.example.movieticket.http.ResponseHTTP;
import com.example.movieticket.http.ResponseHTTP2;
import com.example.movieticket.http.ServiceGenerator;
import com.example.movieticket.interfaces.DetailActivityUtils;
import com.example.movieticket.interfaces.HandleAPIData;
import com.example.movieticket.models.Constant;
import com.example.movieticket.models.FavoriteModel;
import com.example.movieticket.models.TicketMovie;
import com.example.movieticket.models.MovieDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteUtils {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edits;
    private ActivityUtils utils;

    public FavoriteUtils(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(Constant.sp, Context.MODE_PRIVATE);
        this.edits = preferences.edit();
        this.utils = new ActivityUtils(context);
    }

    public void loadFavoriteList(int userId, HandleAPIData<List<TicketMovie>> callback) {
        if(userId == -1 ){
            callback.onCallComplete(null);
            return;
        }
        ServiceGenerator.createUserService(context).getFavorite(userId).enqueue(new Callback<ResponseHTTP<List<TicketMovie>>>() {
            @Override
            public void onResponse(Call<ResponseHTTP<List<TicketMovie>>> call, Response<ResponseHTTP<List<TicketMovie>>> response) {
                ResponseHTTP<List<TicketMovie>> responseHTTP = response.body();
                if(response.isSuccessful() && responseHTTP != null) {
                    callback.onCallComplete(responseHTTP.getBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseHTTP<List<TicketMovie>>> call, Throwable throwable) {

            }
        });
    }

    public void addFavorite(FavoriteModel model, DetailActivityUtils callback) {
        if(model.getUserId() == -1) {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            return;
        }

        ServiceGenerator.createUserService(context).createFavorite(model).enqueue(new Callback<ResponseHTTP2>() {
            @Override
            public void onResponse(Call<ResponseHTTP2> call, Response<ResponseHTTP2> response) {
                ResponseHTTP2 responseHTTP2 = response.body();
                if(response.isSuccessful() && responseHTTP2 != null) {
                    DetailUtils utils1 = new DetailUtils(context);
                    utils1.loadMovieDetail(model.getUserId(), model.getMovieId(), callback);
                }
            }

            @Override
            public void onFailure(Call<ResponseHTTP2> call, Throwable throwable) {
                utils.showToast("We have some error about " + throwable.getMessage());
                Log.d("Homeutils", "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void removeFavorite(FavoriteModel model, DetailActivityUtils callback) {
        if (model.getUserId() == -1) {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            return;
        }
        ServiceGenerator.createUserService(context).deleteFavorite(model.getUserId(), model.getMovieId()).enqueue(new Callback<ResponseHTTP2>() {
            @Override
            public void onResponse(Call<ResponseHTTP2> call, Response<ResponseHTTP2> response) {
                ResponseHTTP2 responseHTTP2 = response.body();
                if (response.isSuccessful() && responseHTTP2 != null) {
                    DetailUtils utils1 = new DetailUtils(context);
                    utils1.loadMovieDetail(model.getUserId(), model.getMovieId(), callback);
                }
            }

            @Override
            public void onFailure(Call<ResponseHTTP2> call, Throwable throwable) {
                utils.showToast("We have some error about " + throwable.getMessage());
                Log.d("Homeutils", "onFailure: " + throwable.getMessage());
            }
        });
    }

}
