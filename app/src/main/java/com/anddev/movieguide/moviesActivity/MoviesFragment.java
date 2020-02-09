package com.anddev.movieguide.moviesActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Genre;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.movieActivity.MovieActvity_;
import com.anddev.movieguide.tools.RecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends Fragment {

    View rootView;
    Activity activity;
    Movies movies;
    Genre genres;
    MoviesAdapter adapter;

    @BindView(R.id.movies_list_recycler_view)
    RecyclerView moviesListRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        activity = getActivity();
        ButterKnife.bind(this, rootView);
        moviesListRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        if (movies != null) {
            setData(movies, genres);
        }
        return rootView;
    }

    public void setData(final Movies movies) {

        this.movies = movies;

        adapter = new MoviesAdapter(activity, movies);
        moviesListRecyclerView.setAdapter(adapter);

        moviesListRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), moviesListRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), MovieActvity_.class);
                        intent.putExtra("Id", movies.getResults().get(position).getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                })
        );


    }

    public void setData(final Movies movies, Genre genres) {

        this.movies = movies;

        adapter = new MoviesAdapter(activity, movies, genres);
        moviesListRecyclerView.setAdapter(adapter);
    }

    public void setGenres(Genre genres) {
        if (adapter != null) {
            adapter.setGenres(genres);
        }
    }
}


