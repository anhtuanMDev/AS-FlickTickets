package com.example.movieticket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.example.movieticket.http.ResponseHTTP;
import com.example.movieticket.http.ServiceGenerator;
import com.example.movieticket.interfaces.TokenVerificationCallback;
import com.example.movieticket.models.Constant;
import com.example.movieticket.models.UserModel;
import com.example.movieticket.models.VerfiyToken;

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
    }

    public void verifyToken(View rootView, TokenVerificationCallback callback) {
        try {
            if (preferences.contains(Constant.sp_token)) {
                String token = preferences.getString(Constant.sp_token, "");
                String username = preferences.getString(Constant.sp_username, "");

                VerfiyToken verifyInfo = new VerfiyToken(token, username);
//                DuoStringModel verifyInfo = new DuoStringModel();
//                verifyInfo.addField("token",token);
//                verifyInfo.addField("username",username);

                ServiceGenerator.createUserService(context)
                        .verifyToken(verifyInfo)
                        .enqueue(new Callback<ResponseHTTP<UserModel>>() {
                            @Override
                            public void onResponse(Call<ResponseHTTP<UserModel>> call, Response<ResponseHTTP<UserModel>> response) {
                                if (response.isSuccessful()) {
                                    ResponseHTTP<UserModel> responseBody = response.body();
                                    if (responseBody != null && responseBody.isStatus()) {
                                        utils.showSnackBar("Token verification successful", rootView);
                                        callback.onVerificationComplete(true);
                                    } else {
                                        utils.showSnackBar("Token verification failed. Response body: " + responseBody, rootView);
                                        callback.onVerificationComplete(false);
                                    }
                                } else {
                                    utils.showSnackBar("Unsuccessful response. Status code: " + response.code(), rootView);
                                    callback.onVerificationComplete(false);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseHTTP<UserModel>> call, Throwable throwable) {
                                utils.showSnackBar("Failed to verify token with failure: " + throwable.getMessage(), rootView);
                                callback.onVerificationComplete(false);
                            }
                        });
            } else {
                utils.showSnackBar("Token not found", rootView);
                callback.onVerificationComplete(false);
            }
        } catch (Exception e) {
            utils.showSnackBar("Error verifying token: " + e.getMessage(), rootView);
            callback.onVerificationComplete(false);
        }
    }

}
