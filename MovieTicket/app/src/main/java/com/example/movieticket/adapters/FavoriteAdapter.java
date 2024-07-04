package com.example.movieticket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieticket.R;
import com.example.movieticket.databinding.AboutMovieItemBinding;
import com.example.movieticket.models.DisplayMovie;
import com.example.movieticket.models.TicketMovie;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<TicketMovie> movies;

    public FavoriteAdapter(List<TicketMovie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_movie_item,null);
        return new FavoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private AboutMovieItemBinding bind;
        ImageView imgPoster;
        TextView txtName, txtTag, txtRelease, txtDuration;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bind = AboutMovieItemBinding.bind(itemView);
            imgPoster = bind.abooutMoviePoster;
            txtName = bind.abooutMovieName;
            txtTag = bind.aboutMovieTag;
            txtRelease = bind.aboutMovieRelease;
            txtDuration = bind.aboutMovieDuration;
        }

        public void bind(TicketMovie movie) {
            String poster = movie.getPoster();
            String name = movie.getTitle();
            String tag = movie.getTags();
            String release = movie.getRelease();
            String duration = movie.getDuration();

            Glide.with(itemView.getContext())
                    .load(poster)
                    .into(imgPoster);
            txtName.setText(name);
            txtTag.setText(tag);
            txtRelease.setText(release);
            txtDuration.setText(duration);
        }
    }

}
