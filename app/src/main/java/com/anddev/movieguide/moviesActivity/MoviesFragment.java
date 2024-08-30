package com.anddev.movieguide.moviesActivity;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Genre;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.PreferenceTools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends Fragment {

    View rootView;
    Activity activity;
    Movies movies;
    Genre genres;
    MoviesAdapter adapter;
    MoviesGridViewAdapter adapterGridView;

    @BindView(R.id.movies_list_recycler_view)
    public RecyclerView moviesListRecyclerView;
    int viewType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        activity = getActivity();
        ButterKnife.bind(this, rootView);
        PreferenceTools.initializePreferenceLibrary(activity);
        if (movies != null) {
            initializeRecyclerViewAndSetAdapter();
        }
        return rootView;
    }

    public void setViewType(int viewType) {
        if (viewType >= 0) {
            this.viewType = viewType;
            PreferenceTools.saveTypeOfView(viewType, PreferenceTools.SAVE_MOVIES, activity);
        }
    }

    public void initializeRecyclerViewAndSetAdapter() {
        Log.d("MOVIES_FRAGMENT", "initialize view");
        viewType = PreferenceTools.getTypeOfView(ActionBarTools.NORMAL_VIEW, PreferenceTools.SAVE_MOVIES, activity);

        try {
            if (viewType == ActionBarTools.GRID1_VIEW) {
                moviesListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
                adapterGridView = new MoviesGridViewAdapter(activity, movies);
                moviesListRecyclerView.setAdapter(adapterGridView);

            } else if (viewType == ActionBarTools.GRID2_VIEW) {
                moviesListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
                adapterGridView = new MoviesGridViewAdapter(activity, movies);
                moviesListRecyclerView.setAdapter(adapterGridView);


            } else if (viewType == ActionBarTools.GRID3_VIEW) {
                moviesListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
                adapterGridView = new MoviesGridViewAdapter(activity, movies);
                moviesListRecyclerView.setAdapter(adapterGridView);


            } else if (viewType == ActionBarTools.GRID4_VIEW) {
                moviesListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
                adapterGridView = new MoviesGridViewAdapter(activity, movies);
                moviesListRecyclerView.setAdapter(adapterGridView);


            } else {
                moviesListRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                if (genres == null) {
                    adapter = new MoviesAdapter(activity, movies);

                } else {
                    adapter = new MoviesAdapter(activity, movies, genres);

                }
                moviesListRecyclerView.setAdapter(adapter);

            }

        } catch (Exception e) {

        }


    }

    public void setData(final Movies movies) {

        this.movies = movies;

        initializeRecyclerViewAndSetAdapter();

    }

    public void setData(final Movies movies, Genre genres) {

        this.movies = movies;
        this.genres = genres;

        initializeRecyclerViewAndSetAdapter();
    }

    public void setGenres(Genre genres) {
        if (adapter != null) {
            adapter.setGenres(genres);
        }
    }

    public void addData(Movies movies) {

        try {
            this.movies.getResults().addAll(movies.getResults());
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            if (adapterGridView != null) {
                adapterGridView.notifyDataSetChanged();
            }
        } catch (Exception e) {
        }
    }
}


