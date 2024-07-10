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
import com.example.movieticket.interfaces.FragmentHomeUtils;
import com.example.movieticket.models.DisplayMovie;

import java.util.List;

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.HomeRecommendViewHolder> {

    private List<DisplayMovie> movies;
    private int selectedPosition = -1; // Field to track the selected position
    private FragmentHomeUtils fragmentHomeUtils; // Add this field

    public HomeRecommendAdapter(List<DisplayMovie> movies, FragmentHomeUtils fragmentHomeUtils) {
        this.movies = movies;
        this.fragmentHomeUtils = fragmentHomeUtils; // Initialize the field
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
        holder.bind(recommendation, position == selectedPosition, fragmentHomeUtils);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousPosition);
        notifyItemChanged(position);

        // Update the TextViews through the interface method
        DisplayMovie selectedMovie = getMovieAt(position);
        fragmentHomeUtils.changeRecommendMovie(selectedMovie.getTitle(), selectedMovie.getTags());
    }

    public DisplayMovie getMovieAt(int position) {
        return movies.get(position);
    }

    public class HomeRecommendViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;

        public HomeRecommendViewHolder(View itemView) {
            super(itemView);
            // Initialize your views here using findViewById
            imgPoster = itemView.findViewById(R.id.recommend_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        setSelectedPosition(position);
                    }
                }
            });
        }

        public void bind(DisplayMovie recommendation, boolean isSelected, FragmentHomeUtils callback) {
            Glide.with(itemView.getContext())
                    .load(recommendation.getPoster())
                    .placeholder(R.drawable.image_pending)
                    .error(R.drawable.image_onerror)
                    .into(imgPoster);
            if(isSelected){
                callback.changeRecommendMovie(recommendation.getTitle(), recommendation.getTags());

            }
            // Adjust the size of the image based on whether it is selected or not
        }
    }
}
