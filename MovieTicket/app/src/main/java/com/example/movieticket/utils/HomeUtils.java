package com.example.movieticket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.example.movieticket.http.ResponseHTTP;
import com.example.movieticket.http.ServiceGenerator;
import com.example.movieticket.interfaces.FragmentHomeUtils;
import com.example.movieticket.interfaces.MainActivityUtils;
import com.example.movieticket.models.Constant;
import com.example.movieticket.models.DisplayMovie;
import com.example.movieticket.models.MinimumMovie;
import com.example.movieticket.models.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUtils {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edits;
    private ActivityUtils utils;

    public HomeUtils(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(Constant.sp, Context.MODE_PRIVATE);
        this.edits = preferences.edit();
        this.utils = new ActivityUtils(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void performSeach(String text) {
        if(text.isEmpty()) {
            utils.showToast("please input something");
            return;
        }

    }

    public void getMovieData(View rootView, FragmentHomeUtils callback, int page) {
        ServiceGenerator.createMovieService(context).getMovies(page).enqueue(new Callback<ResponseHTTP<MovieModel>>() {
            @Override
            public void onResponse(Call<ResponseHTTP<MovieModel>> call, Response<ResponseHTTP<MovieModel>> response) {
                ResponseHTTP<MovieModel> responseHTTP = response.body();
                MovieModel data = responseHTTP.getBody();
                if (response.isSuccessful() && data != null) {
                        callback.getMovieDataComplete(data);
                }
            }

            @Override
            public void onFailure(Call<ResponseHTTP<MovieModel>> call, Throwable throwable) {
                utils.showSnackBar("We have some error about " + throwable.getMessage(), rootView);
                Log.d("Homeutils", "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void getRecommendData(View rootView, FragmentHomeUtils callback, int page) {
        ServiceGenerator.createMovieService(context).getRecommend(page).enqueue(new Callback<ResponseHTTP<List<DisplayMovie>>>() {
            @Override
            public void onResponse(Call<ResponseHTTP<List<DisplayMovie>>> call, Response<ResponseHTTP<List<DisplayMovie>>> response) {
                ResponseHTTP<List<DisplayMovie>> responseHTTP = response.body();
                List<DisplayMovie> data = responseHTTP.getBody();
                if (response.isSuccessful() && data != null) {
                        callback.getRecommendComplete(data);
                    utils.showSnackBar("Get recommend successfully", rootView);
                }else {
                    utils.showSnackBar("Get recommend fail", rootView);
                }
            }

            @Override
            public void onFailure(Call<ResponseHTTP<List<DisplayMovie>>> call, Throwable throwable) {
                utils.showSnackBar("We have some error about " + throwable.getMessage(), rootView);
                Log.d("Homeutils", "onFailure: "+ throwable.getMessage());
            }
        });
    }

    public void getDisplayData(View rootView, FragmentHomeUtils callback, int page) {
        ServiceGenerator.createMovieService(context).getDisplay(page).enqueue(new Callback<ResponseHTTP<List<MinimumMovie>>>() {
            @Override
            public void onResponse(Call<ResponseHTTP<List<MinimumMovie>>> call, Response<ResponseHTTP<List<MinimumMovie>>> response) {
                ResponseHTTP<List<MinimumMovie>> responseHTTP = response.body();
                List<MinimumMovie> data = responseHTTP.getBody();
                if (response.isSuccessful() && data != null) {
                        callback.getDisplayComplete(data);
                }
            }

            @Override
            public void onFailure(Call<ResponseHTTP<List<MinimumMovie>>> call, Throwable throwable) {
                utils.showSnackBar("We have some error about " + throwable.getMessage(), rootView);
                Log.d("Homeutils", "onFailure: " + throwable.getMessage());
            }
        });
    }

}
