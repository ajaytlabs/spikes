package com.novoda.movies;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DemoMainActivity extends Activity {

    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        List<Movie> movies = createListOfMovies();
        MovieItemView.Listener listener = createListener();
        recyclerView.setAdapter(new MoviesAdapter(listener, movies));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private MovieItemView.Listener createListener() {
        return new MovieItemView.Listener() {

            @Override
            public void onClick(Movie movie) {
                toast("click " + movie.name);
            }

            @Override
            public void onClickPlay(Movie movie) {
                toast("click play " + movie.name);
            }

            @Override
            public void onClickFavorite(Movie movie) {
                toast("click fav " + movie.name);
            }

        };
    }

    private void toast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private List<Movie> createListOfMovies() {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            movies.add(new Movie("Movie " + i));
        }
        return movies;
    }

    private static class MoviesAdapter extends RecyclerView.Adapter<MovieItemViewHolder> {

        private final MovieItemView.Listener listener;
        private final List<Movie> movies;

        MoviesAdapter(MovieItemView.Listener listener, List<Movie> movies) {
            super.setHasStableIds(true);
            this.listener = listener;
            this.movies = movies;
        }

        @Override
        public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return MovieItemViewHolder.inflate(parent, LayoutInflater.from(parent.getContext()));
        }

        @Override
        public void onViewAttachedToWindow(MovieItemViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            holder.itemView.attachListener(listener);
        }

        @Override
        public void onBindViewHolder(MovieItemViewHolder holder, int position) {
            Movie movie = movies.get(position);
            holder.itemView.bind(movie);
        }

        @Override
        public void onViewDetachedFromWindow(MovieItemViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.itemView.detachListeners();
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }

    private static final class MovieItemViewHolder extends RecyclerView.ViewHolder {

        final MovieItemView itemView;

        static MovieItemViewHolder inflate(ViewGroup parent, LayoutInflater inflater) {
            MovieItemView movieItemView = (MovieItemView) inflater.inflate(R.layout.view_movie_item, parent, false);
            return new MovieItemViewHolder(movieItemView);
        }

        private MovieItemViewHolder(MovieItemView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

}
