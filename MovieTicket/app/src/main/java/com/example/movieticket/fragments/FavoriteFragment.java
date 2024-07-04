package com.example.movieticket.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieticket.FakeData2;
import com.example.movieticket.R;
import com.example.movieticket.adapters.FavoriteAdapter;
import com.example.movieticket.databinding.FragmentFavoriteBinding;


public class FavoriteFragment extends Fragment {
    private FragmentFavoriteBinding bind;
    private RecyclerView favRcl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         bind  = FragmentFavoriteBinding.inflate(inflater, container, false);
         favRcl = bind.favList;
        favRcl.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        favRcl.setAdapter(new FavoriteAdapter(FakeData2.recommend));
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}