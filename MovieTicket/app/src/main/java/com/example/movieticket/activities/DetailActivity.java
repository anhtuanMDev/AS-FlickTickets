package com.example.movieticket.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieticket.R;
import com.example.movieticket.databinding.ActivityDetailBinding;
import com.example.movieticket.http.ResponseHTTP;
import com.example.movieticket.http.ServiceGenerator;
import com.example.movieticket.interfaces.DetailActivityUtils;
import com.example.movieticket.models.Constant;
import com.example.movieticket.models.FavoriteModel;
import com.example.movieticket.models.MovieDetail;
import com.example.movieticket.utils.DetailUtils;
import com.example.movieticket.utils.FavoriteUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements DetailActivityUtils {

    private ActivityDetailBinding bind;
    private Toolbar toolbar;
    private TextView txtTitle, txtName, txtTag, txtAbout, txtTrailer, txtCast, txtRate;
    private TextView txtReviews, txtDescr, txtYear, txtDuration, txtTicketsSold;
    private ImageView imgPoster, imgBanner, imgBookmark, imgPlacement;
    private RecyclerView rclCast, rclReviews;
    private WebView webViewTrailer;
    private Spinner spnSlot;
    private String movieId;
    private int userId;
    private SharedPreferences preferences;
    private DetailUtils utils;
    private FavoriteUtils favoriteUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bind = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        // binding view
        preferences = getSharedPreferences(Constant.sp, MODE_PRIVATE);
        toolbar = bind.detailToolbar;
        txtTitle = bind.detailTitle;
        txtName = bind.detailName;
        txtRate = bind.detailRating;
        txtTag = bind.detailTag;
        txtAbout = bind.detailAbout;
        txtTrailer = bind.detailTrailer;
        txtCast = bind.detailCast;
        txtReviews = bind.detailReviews;
        txtYear = bind.detailYear;
        txtDuration = bind.detailDuration;
        txtTicketsSold = bind.detailTicketSold;
        txtDescr = bind.detailDescription;

        imgPoster = bind.detailPoster;
        imgBanner = bind.detailBanner;
        imgBookmark = bind.detailBookmark;
        imgPlacement = bind.placement;

        rclCast = bind.detailCastCP;
        rclReviews = bind.detailReviewsCP;

        webViewTrailer = bind.detailTrailerCP;
        spnSlot = bind.timeSlot;

        toolbar.setTitle("");
        movieId = getIntent().getStringExtra("MOVIE_ID");
        userId = preferences.getInt(Constant.sp_userid, 0);
        utils = new DetailUtils(this);
        utils.loadMovieDetail(userId, Integer.parseInt(movieId), this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onDetailLoadComplete(MovieDetail data) {
        txtTitle.setText("Movie's Detail");
        txtName.setText(data.getTitle());
        txtTag.setText(data.getGenres());
        txtYear.setText(data.getReleaseDate());
        txtRate.setText(data.getRate()+"");
        txtDuration.setText(data.getDuration() + " Minutes");
        txtDescr.setText(data.getDescr());

        imgBookmark.setImageResource(data.isFavorite() ? R.drawable.bookmark_bold : R.drawable.bookmark_linear);

        imgBookmark.setOnClickListener(v-> {
            favoriteUtils = new FavoriteUtils(this);
            if (data.isFavorite()) {
                favoriteUtils.removeFavorite(new FavoriteModel(userId, data.getId()), this);
            } else {
                favoriteUtils.addFavorite(new FavoriteModel(userId, data.getId()), this);
            }
        });

        Glide.with(this).load(data.getPoster())
                .placeholder(R.drawable.image_pending)
                .error(R.drawable.image_onerror)
                .into(imgPoster);
        Glide.with(this).load(data.getBanner())
                .placeholder(R.drawable.banner)
                .error(R.drawable.image_onerror)
                .into(imgBanner);
    }
}
