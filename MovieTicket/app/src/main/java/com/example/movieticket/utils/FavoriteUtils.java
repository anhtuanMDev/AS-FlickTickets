package com.example.movieticket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.movieticket.http.ResponseHTTP;
import com.example.movieticket.http.ServiceGenerator;
import com.example.movieticket.interfaces.DetailActivityUtils;
import com.example.movieticket.models.Constant;
import com.example.movieticket.models.MovieDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUtils {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edits;
    private ActivityUtils utils;

    public DetailUtils(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(Constant.sp, Context.MODE_PRIVATE);
        this.edits = preferences.edit();
        this.utils = new ActivityUtils(context);
    }

    public void loadMovieDetail(int userId, int movieId, DetailActivityUtils callback){
        ServiceGenerator.createMovieService(context).getMovieDetail(movieId, userId).enqueue(new Callback<ResponseHTTP<MovieDetail>>() {
            @Override
            public void onResponse(Call<ResponseHTTP<MovieDetail>> call, Response<ResponseHTTP<MovieDetail>> response) {
                ResponseHTTP<MovieDetail> responseHTTP = response.body();
                if(response.isSuccessful() && responseHTTP.getBody() != null){
                    callback.onDetailLoadComplete(responseHTTP.getBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseHTTP<MovieDetail>> call, Throwable throwable) {
                utils.showToast("We have some error about " + throwable.getMessage());
                Log.d("Homeutils", "onFailure: " + throwable.getMessage());
            }
        });
    }
}
