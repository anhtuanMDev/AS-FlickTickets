package com.example.movieticket.interfaces;

import com.example.movieticket.models.DisplayMovie;
import com.example.movieticket.models.MinimumMovie;
import com.example.movieticket.models.MovieModel;

import java.util.List;

public interface FragmentHomeUtils {
    void changeRecommendMovie(String name, String tags);
    void getMovieDataComplete(MovieModel data);
    void getRecommendComplete(List<DisplayMovie> data);
    void getDisplayComplete(List<MinimumMovie> data);
}
