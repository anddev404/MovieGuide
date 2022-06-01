package com.anddev.movieguide.tvShows;

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
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.PreferenceTools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowsFragment extends Fragment {

    View rootView;
    Activity activity;
    TvShows tvShows;
    TvShowsAdapter adapter;
    TvShowsGridViewAdapter adapterGridView;

    @BindView(R.id.movies_list_recycler_view)
    public RecyclerView tvShowsListRecyclerView;
    int viewType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        activity = getActivity();
        ButterKnife.bind(this, rootView);
        PreferenceTools.initializePreferenceLibrary(activity);

        if (tvShows != null) {
            initializeRecyclerViewAndSetAdapter();
        }
        return rootView;
    }

    public void setData(final TvShows tvShows) {

        this.tvShows = tvShows;

        initializeRecyclerViewAndSetAdapter();
    }

    public void addData(TvShows tvShows) {

        try {
            this.tvShows.getResults().addAll(tvShows.getResults());
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void setViewType(int viewType) {
        if (viewType >= 0) {
            this.viewType = viewType;
            PreferenceTools.saveTypeOfView(viewType, PreferenceTools.SAVE_MOVIES, activity);
        }
    }

    public void initializeRecyclerViewAndSetAdapter() {
        Log.d("TV_SHOWS_FRAGMENT", "initialize view");
        viewType = PreferenceTools.getTypeOfView(ActionBarTools.NORMAL_VIEW, PreferenceTools.SAVE_MOVIES, activity);

        try {
            if (viewType == ActionBarTools.GRID1_VIEW) {
                tvShowsListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
                adapterGridView = new TvShowsGridViewAdapter(activity, tvShows);
                tvShowsListRecyclerView.setAdapter(adapterGridView);

            } else if (viewType == ActionBarTools.GRID2_VIEW) {
                tvShowsListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
                adapterGridView = new TvShowsGridViewAdapter(activity, tvShows);
                tvShowsListRecyclerView.setAdapter(adapterGridView);


            } else if (viewType == ActionBarTools.GRID3_VIEW) {
                tvShowsListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
                adapterGridView = new TvShowsGridViewAdapter(activity, tvShows);
                tvShowsListRecyclerView.setAdapter(adapterGridView);


            } else if (viewType == ActionBarTools.GRID4_VIEW) {
                tvShowsListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
                adapterGridView = new TvShowsGridViewAdapter(activity, tvShows);
                tvShowsListRecyclerView.setAdapter(adapterGridView);


            } else {
                tvShowsListRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                adapter = new TvShowsAdapter(activity, tvShows);
                tvShowsListRecyclerView.setAdapter(adapter);

            }

        } catch (Exception e) {

        }


    }
}

