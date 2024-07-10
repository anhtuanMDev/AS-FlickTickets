package com.example.movieticket.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.movieticket.FakeData;
import com.example.movieticket.R;
import com.example.movieticket.adapters.HomeRecommendAdapter;
import com.example.movieticket.adapters.MovieAdapter;
import com.example.movieticket.databinding.FragmentHomeBinding;
import com.example.movieticket.interfaces.FragmentHomeUtils;
import com.example.movieticket.models.DisplayMovie;
import com.example.movieticket.models.MinimumMovie;
import com.example.movieticket.models.MovieModel;
import com.example.movieticket.utils.HomeUtils;
import com.example.movieticket.utils.MainUtils;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class HomeFragment extends Fragment implements FragmentHomeUtils{

    private FragmentHomeBinding bind;
    private RecyclerView movieRcl;
    private TextView txtRcmName, txtRcmTag;
    private TextView edtPlay, edtComing, edtToprate, edtPopular, edtSearch;
    private MainUtils utils;
    private ViewPager2 slider;
    private Handler sliderHandler = new Handler();
    private HomeUtils functUtils;
    private List<DisplayMovie> recommendList;
    private List<MinimumMovie> displayMovieList;
    private HomeRecommendAdapter adapter;
    private MovieAdapter movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = new MainUtils(getContext());
        functUtils = new HomeUtils(getContext());
        recommendList = new ArrayList<>();
        displayMovieList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentHomeBinding.inflate(inflater, container, false);
        slider = bind.homeRecommend;
        movieRcl = bind.homeMovie;
        edtPlay = bind.homePlay;
        edtComing = bind.homeComing;
        edtToprate = bind.homeToprate;
        edtPopular = bind.homePopular;
        edtSearch = bind.homeSearch;
        txtRcmName = bind.homeRecommendName;
        txtRcmTag = bind.homeRecommendTag;

        functUtils.getMovieData(bind.getRoot(), this, 1);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HomeRecommendAdapter(recommendList, this);
        movieAdapter = new MovieAdapter(displayMovieList);
        slider.setAdapter(adapter);
        movieRcl.setAdapter(movieAdapter);

        slider.setOffscreenPageLimit(3);
        slider.setClipChildren(false);
        slider.setClipToPadding(false);
        slider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(5));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        slider.setPageTransformer(transformer);
        slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);

                if (adapter != null) {
                    adapter.setSelectedPosition(position);
                }
            }
        });

        // Set up the listener for search edit text
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Drawable end = ContextCompat.getDrawable(getContext(),
                            R.drawable.search_bold);
                    edtSearch.setText("");
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, end, null);

                } else {
                    Drawable end = ContextCompat.getDrawable(getContext(),
                            R.drawable.search_linear);
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, end, null);
                }
            }
        });
        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Perform search action here

                // Dismiss keyboard and clear focus from EditText
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                edtSearch.clearFocus();

                return true; // Return true to indicate that the action has been handled
            }
            return false; // Return false if you don't handle the action
        });

        edtPlay.setOnClickListener(v -> {
            // Handle play click event
        });
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = slider.getCurrentItem();
            int itemCount = slider.getAdapter() != null ? slider.getAdapter().getItemCount() : 0;
            if (currentItem == itemCount - 1) {
                slider.setCurrentItem(0); // Go back to the first item
            } else {
                slider.setCurrentItem(currentItem + 1);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    public void changeRecommendMovie(String name, String tags) {
        txtRcmName.setText(name);
        txtRcmTag.setText(tags);
    }

    @Override
    public void getMovieDataComplete(MovieModel data) {
        displayMovieList.clear();
        recommendList.clear();
        displayMovieList.addAll(data.getDisplay());
        recommendList.addAll(data.getRecommend());
        movieAdapter.notifyItemRangeChanged(0,12);
        adapter.notifyItemRangeChanged(0,10);
    }

    @Override
    public void getRecommendComplete(List<DisplayMovie> data) {
        recommendList.clear();
        recommendList.addAll(data);
        adapter.notifyItemRangeChanged(0,10);
    }

    @Override
    public void getDisplayComplete(List<MinimumMovie> data) {
        int start = displayMovieList.size();
        displayMovieList.addAll(data);
        movieAdapter.notifyItemRangeChanged(start, 12);
    }
}
