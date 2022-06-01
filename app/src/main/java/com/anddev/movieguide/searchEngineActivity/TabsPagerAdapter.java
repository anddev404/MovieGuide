package com.anddev.movieguide.searchEngineActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anddev.movieguide.R;
import com.anddev.movieguide.moviesActivity.MoviesFragment;
import com.anddev.movieguide.peopleActivity.PeopleFragment;
import com.anddev.movieguide.tools.MyApplication;
import com.anddev.movieguide.tvShows.TvShowsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public static String[] tabs = {MyApplication.getStringFromResource(R.string.movies), MyApplication.getStringFromResource(R.string.tv_shows), MyApplication.getStringFromResource(R.string.people)};

    public static String[] getTabs() {
        return tabs;
    }

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {


            case 0:

                return new MoviesFragment();

            case 1:

                return new TvShowsFragment();

            case 2:

                return new PeopleFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
