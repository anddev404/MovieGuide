package com.anddev.movieguide.tools;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.favouriteActivity.FavouriteActivity_;
import com.anddev.movieguide.moviesActivity.MoviesActivity_;
import com.anddev.movieguide.peopleActivity.PeopleActivity_;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity_;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationDrawerTools {

    Activity activity;
    DrawerLayout mDrawerLayout;

    @BindView(R.id.people_navigation_drawer_button)
    LinearLayout peopleButton;
    @BindView(R.id.movies_navigation_drawer_button)
    LinearLayout moviesButton;


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

    @OnClick(R.id.favourites_navigation_drawer_button)
    public void onClickFavourite() {

        Intent intent = new Intent(activity, FavouriteActivity_.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.setting_navigation_drawer_button)
    public void onClickOptions() {

        Toast.makeText(activity, "" + activity.getString(R.string.available_soon_navigation_drawer), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.contact_developer_navigation_drawer_button)
    public void onClickContactDeveloper() {
        try {
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            /* Fill it with Data */
            emailIntent.setType("text/plain");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"anddev404@gmail.com"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Movie Guide");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
            /* Send it off to the Activity-Chooser */
            activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (Exception e) {
            Toast.makeText(activity, "" + activity.getString(R.string.available_soon_navigation_drawer), Toast.LENGTH_SHORT).show();

        }

    }

    public NavigationDrawerTools setOtherColorForMoviesButton() {

        moviesButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        peopleButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));

        return this;
    }

    public NavigationDrawerTools setOtherColorForPeopleButton() {

        moviesButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        peopleButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));

        return this;
    }

    public NavigationDrawerTools setNormalColorForAllButtons() {

        moviesButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        peopleButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));

        return this;
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

    public boolean closeNavigationDrawerIfOpen() {
        if (mDrawerLayout != null) {

            if (mDrawerLayout.isDrawerOpen((int) Gravity.LEFT)) {

                mDrawerLayout.closeDrawer((int) Gravity.LEFT);
                return true;
            }
        }
        return false;
    }
}