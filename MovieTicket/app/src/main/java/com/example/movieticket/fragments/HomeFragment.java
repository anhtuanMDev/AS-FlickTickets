package com.example.movieticket.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticket.FakeData;
import com.example.movieticket.R;
import com.example.movieticket.activities.LoginActivity;
import com.example.movieticket.adapters.HomeRecommendAdapter;
import com.example.movieticket.adapters.MovieAdapter;
import com.example.movieticket.databinding.FragmentHomeBinding;
import com.example.movieticket.utils.HomeUtils;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding bind;
    private RecyclerView recommendRcl, movieRcl;
    private TextView edtPlay, edtComing, edtToprate, edtPopular, edtSearch;
    private HomeUtils utils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = new HomeUtils(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentHomeBinding.inflate(inflater, container, false);
        recommendRcl = bind.homeRecommend;
        movieRcl = bind.homeMovie;
        edtPlay = bind.homePlay;
        edtComing = bind.homeComing;
        edtToprate = bind.homeToprate;
        edtPopular = bind.homePopular;
        edtSearch = bind.homeSearch;
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set LayoutManager
        recommendRcl.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendRcl.setAdapter(new HomeRecommendAdapter(FakeData.recommend));
        movieRcl.setAdapter(new MovieAdapter(FakeData.recommend));

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

                }else {
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
//                performSearch();

                // Dismiss keyboard and clear focus from EditText
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                edtSearch.clearFocus();

                return true; // Return true to indicate that the action has been handled
            }
            return false; // Return false if you don't handle the action
        });

        edtPlay.setOnClickListener(v -> {
        });
    }
}