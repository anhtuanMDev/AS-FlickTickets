package com.example.movieticket.activities;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieticket.R;
import com.example.movieticket.databinding.ActivityLoginBinding;
import com.example.movieticket.http.HandleImage;
import com.example.movieticket.http.ResponseHTTP;
import com.example.movieticket.http.ServiceGenerator;
import com.example.movieticket.models.Constant;
import com.example.movieticket.models.LoginModel;
import com.example.movieticket.models.UserModel;
import com.example.movieticket.utils.ActivityUtils;
import com.example.movieticket.utils.PreferecnceUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding bind;
    private EditText edtUsername, edtPassword;
    private Button btnSignin;
    private String KEY_NAME, KEY_TOKEN, KEY_USERNAME, KEY_PASSWORD, KEY_ROLE, KEY_ID, KEY_AVATAR;
    private ActivityUtils functionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bind = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        functionUtils = new ActivityUtils(this);

        edtUsername = bind.loginUsername;
        edtPassword = bind.loginPassword;
        btnSignin = bind.loginSignin;

        KEY_ID = Constant.sp_userid;
        KEY_NAME = Constant.sp_name;
        KEY_TOKEN = Constant.sp_token;
        KEY_ROLE = Constant.sp_userRole;
        KEY_USERNAME = Constant.sp_username;
        KEY_PASSWORD = Constant.sp_password;
        KEY_AVATAR = Constant.sp_avatarURL;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // set example login info
        edtUsername.setText("anhtt676@gmail.com");
        edtPassword.setText("123456789");

        btnSignin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            login(username, password, v);
        });
    }

    public void login(String username, String password, View rootView) {
        try {
            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Please input all fields");
            }
            LoginModel infor = new LoginModel(username, password);
            ServiceGenerator.createUserService(this).login(infor).enqueue(new Callback<ResponseHTTP<UserModel>>() {
                @Override
                public void onResponse(Call<ResponseHTTP<UserModel>> call, Response<ResponseHTTP<UserModel>> response) {
                    if (response.isSuccessful()) {
                        ResponseHTTP<UserModel> body = response.body();
                        if (body != null && body.isStatus()) {
                            UserModel user = body.getBody();
                            PreferecnceUtils utils = new PreferecnceUtils(LoginActivity.this);
                            HashMap<String, Object> input = new HashMap<>();
                            input.put(KEY_ID, user.getId());
                            input.put(KEY_NAME, user.getName());
                            input.put(KEY_ROLE, user.getRole());
                            input.put(KEY_PASSWORD, user.getPassword());
                            input.put(KEY_USERNAME, user.getUsername());
                            input.put(KEY_TOKEN, user.getToken());

                            // Handle separately avatar
                            byte[] avatar = user.getAvatar();
                            if (avatar != null) {
                                HandleImage handleImage = new HandleImage(LoginActivity.this);
                                handleImage.convertImageString(avatar);
                            }
                            utils.apply(input);
                            finish();
                        } else {
                            // Handle unsuccessful login (optional)
                            functionUtils.showSnackBar("Login failed", rootView);
                        }
                    } else {
                        functionUtils.showSnackBar("Login failed", rootView);
                    }
                }

                @Override
                public void onFailure(Call<ResponseHTTP<UserModel>> call, Throwable throwable) {
                    functionUtils.showSnackBar("Login failed: " + throwable.getMessage(), rootView);
                    Log.e("LoginActivity", "Login failed", throwable);
                }
            });
        } catch (IllegalArgumentException e) {
            functionUtils.showSnackBar(e.getMessage(), rootView);
        }
    }
}
