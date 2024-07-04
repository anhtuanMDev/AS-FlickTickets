package com.example.movieticket.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieticket.R;
import com.example.movieticket.databinding.FragmentDetailMovieBinding;
import com.example.movieticket.databinding.MovieItemBinding;
import com.example.movieticket.models.DisplayMovie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<DisplayMovie> movies;

    public MovieAdapter(List<DisplayMovie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, null);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movie;
        private MovieItemBinding bind;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bind = MovieItemBinding.bind(itemView);
            movie = bind.moviePoster;
        }

        public void bind(DisplayMovie movies) {
            String poster = movies.getPoster();
            Glide.with(itemView.getContext())
                    .load(poster)
                    .into(movie);
        }
    }


}
