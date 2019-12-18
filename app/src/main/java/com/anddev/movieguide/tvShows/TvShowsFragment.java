package com.anddev.movieguide.tvShows;

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
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.movieActivity.MovieActvity_;
import com.anddev.movieguide.moviesActivity.MoviesAdapter;
import com.anddev.movieguide.tools.RecyclerItemClickListener;
import com.anddev.movieguide.tvShow.TvShowActivity;
import com.anddev.movieguide.tvShow.TvShowActivity_;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowsFragment extends Fragment {

    View rootView;
    Activity activity;
    TvShows tvShows;

    @BindView(R.id.tv_shows_list_recycler_view)
    RecyclerView tvShowsListRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tv_shows, container, false);
        activity = getActivity();
        ButterKnife.bind(this, rootView);
        tvShowsListRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        return rootView;
    }

    public void setData(final TvShows tvShows) {

        this.tvShows = tvShows;

        TvShowsAdapter adapter = new TvShowsAdapter(activity, tvShows);
        tvShowsListRecyclerView.setAdapter(adapter);

        tvShowsListRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), tvShowsListRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), TvShowActivity_.class);
                        intent.putExtra("Id", tvShows.getResults().get(position).getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                })
        );


    }
}


