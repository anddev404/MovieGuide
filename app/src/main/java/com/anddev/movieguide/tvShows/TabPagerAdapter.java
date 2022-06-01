package com.anddev.movieguide.tvShows;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anddev.movieguide.R;
import com.anddev.movieguide.tools.MyApplication;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static String[] tabs = {
            MyApplication.getStringFromResource(R.string.popular_tv_shows),
            MyApplication.getStringFromResource(R.string.top_rated_tv_shows),
            MyApplication.getStringFromResource(R.string.on_the_air_tv_shows),
            MyApplication.getStringFromResource(R.string.airing_tv_shows)};

    public static String[] getTabs() {
        return tabs;
    }

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {


            case 0:

                return new TvShowsFragment();

            case 1:

                return new TvShowsFragment();

            case 2:

                return new TvShowsFragment();

            case 3:

                return new TvShowsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
