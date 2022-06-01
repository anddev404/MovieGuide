package com.anddev.movieguide.moviesActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anddev.movieguide.R;
import com.anddev.movieguide.tools.MyApplication;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public static String[] tabs = {
            MyApplication.getStringFromResource(R.string.popular_movies),
            MyApplication.getStringFromResource(R.string.top_rated_movies),
            MyApplication.getStringFromResource(R.string.upcoming_movies),
            MyApplication.getStringFromResource(R.string.now_playing_movies)};

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

                return new MoviesFragment();

            case 2:

                return new MoviesFragment();

            case 3:

                return new MoviesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
