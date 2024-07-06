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
import com.example.movieticket.models.DisplayMovie;
import com.example.movieticket.utils.HomeUtils;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding bind;
    private RecyclerView movieRcl;
    private TextView txtRcmName, txtRcmTag;
    private TextView edtPlay, edtComing, edtToprate, edtPopular, edtSearch;
    private HomeUtils utils;
    private ViewPager2 slider;
    private Handler sliderHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = new HomeUtils(getContext());
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
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeRecommendAdapter adapter = new HomeRecommendAdapter(FakeData.recommend, txtRcmName, txtRcmTag);
        slider.setAdapter(adapter);
        movieRcl.setAdapter(new MovieAdapter(FakeData.recommend));

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

                // Update the name and tags TextViews
                updateNameAndTags(position);

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

    private void updateNameAndTags(int position) {
        if (slider.getAdapter() != null) {
            DisplayMovie currentMovie = ((HomeRecommendAdapter) slider.getAdapter()).getMovieAt(position);
            txtRcmName.setText(currentMovie.getTitle());
            txtRcmTag.setText(currentMovie.getTags());
        }
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
}
