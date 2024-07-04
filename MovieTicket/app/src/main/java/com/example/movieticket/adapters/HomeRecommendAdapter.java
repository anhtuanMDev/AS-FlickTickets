package com.example.movieticket.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieticket.R;
import com.example.movieticket.models.DisplayMovie;

import java.util.List;

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.HomeRecommendViewHolder> {

    private List<DisplayMovie> movies; // Assuming 'Recommendation' is your data class

    public HomeRecommendAdapter(List<DisplayMovie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public HomeRecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your item layout here
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommend_item, parent, false);
        return new HomeRecommendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecommendViewHolder holder, int position) {
        // Bind data to the views within the ViewHolder
        DisplayMovie recommendation = movies.get(position);
        holder.bind(recommendation);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class HomeRecommendViewHolder extends RecyclerView.ViewHolder {

        // Declare views from your item layout (e.g., ImageView, TextView)
        private ImageView imgPoster;
        private TextView txtTitle, txtTag;

        public HomeRecommendViewHolder(View itemView) {
            super(itemView);
            // Initialize your views here using findViewById
            imgPoster = itemView.findViewById(R.id.recommend_poster);
            txtTitle = itemView.findViewById(R.id.recommend_name);
            txtTag = itemView.findViewById(R.id.recommend_tag);
        }

        public void bind(DisplayMovie recommendation) {
            // Populate views with data from the Recommendation object
            // Example using Glide for image loading:
            Glide.with(itemView.getContext())
                    .load(recommendation.getPoster())
                    .into(imgPoster);
            txtTitle.setText(recommendation.getTitle());
            txtTag.setText(recommendation.getTags());
        }
    }

}

