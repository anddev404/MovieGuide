package com.anddev.movieguide.tools;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

import com.anddev.movieguide.R;
import com.anddev.movieguide.moviesActivity.MoviesActivity_;
import com.anddev.movieguide.peopleActivity.PeopleActivity_;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity_;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationDrawerTools {

    Activity activity;
    DrawerLayout mDrawerLayout;

    public NavigationDrawerTools(Activity activity) {

        this.activity = activity;
        ButterKnife.bind(this, activity);
    }

    public NavigationDrawerTools(Activity activity, int navigationDrawerLayoutId) {

        this.activity = activity;
        ButterKnife.bind(this, activity);

        mDrawerLayout = (DrawerLayout) activity.findViewById(navigationDrawerLayoutId);

    }

    @OnClick(R.id.people_navigation_drawer_button)
    public void onClickPeople() {

        Intent intent = new Intent(activity, PeopleActivity_.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.movies_navigation_drawer_button)
    public void onClickMovies() {

        Intent intent = new Intent(activity, MoviesActivity_.class);
        activity.startActivity(intent);
    }


    @OnClick(R.id.search_engine_navigation_drawer_button)
    public void onClickSearch() {

        Intent intent = new Intent(activity, SearchEngineActivity_.class);
        activity.startActivity(intent);

    }

    public void openOrCloseNavigationDrawer() {
        if (mDrawerLayout != null) {

            if (mDrawerLayout.isDrawerOpen((int) Gravity.LEFT)) {

                mDrawerLayout.closeDrawer((int) Gravity.LEFT);

            } else {

                mDrawerLayout.openDrawer((int) Gravity.LEFT);

            }
        }
    }
}