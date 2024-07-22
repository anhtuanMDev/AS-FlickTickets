package com.example.movieticket.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieticket.adapters.FavoriteAdapter;
import com.example.movieticket.databinding.FragmentFavoriteBinding;
import com.example.movieticket.interfaces.HandleAPIData;
import com.example.movieticket.models.Constant;
import com.example.movieticket.models.TicketMovie;
import com.example.movieticket.utils.FavoriteUtils;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements HandleAPIData<List<TicketMovie>> {
    private FragmentFavoriteBinding bind;
    private RecyclerView favRcl;
    private List<TicketMovie> list;
    private FavoriteUtils utils;
    private SharedPreferences preferences;
    private FavoriteAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        preferences = getActivity().getSharedPreferences(Constant.sp, Context.MODE_PRIVATE);
        int userId = preferences.getInt(Constant.sp_userid,-1);
        adapter = new FavoriteAdapter(list);
        utils = new FavoriteUtils(getContext());
        utils.loadFavoriteList(userId, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         bind  = FragmentFavoriteBinding.inflate(inflater, container, false);
         favRcl = bind.favList;
        favRcl.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        favRcl.setAdapter(adapter);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCallComplete(List<TicketMovie> data) {
        list.clear();
        list.addAll(data);
        adapter.notifyItemRangeChanged(0, list.size());

    }
}