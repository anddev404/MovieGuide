package com.anddev.movieguide.favouriteActivity;

import android.support.v7.app.AppCompatActivity;

import com.anddev.movieguide.R;
import com.anddev.movieguide.tools.NavigationDrawerTools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_favourite)
public class FavouriteActivity extends AppCompatActivity {

    //region variables

    FavouriteActivity activity;

    NavigationDrawerTools navigationDrawer;

    //endregion

    @AfterViews
    public void onCreate() {
        activity = this;
        navigationDrawer = new NavigationDrawerTools(activity, R.id.favourite_navigation_draver).setOtherColorForFavouriteButton();

    }

}
