package com.anddev.movieguide.searchEngineActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anddev.movieguide.R;
import com.anddev.movieguide.tools.NavigationDrawerTools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_search_engine)
public class SearchEngineActivity extends AppCompatActivity {

    Activity activity;
    NavigationDrawerTools navigationDrawer;

    @AfterViews
    public void onCreate() {
        activity = this;
        navigationDrawer = new NavigationDrawerTools(activity);

    }
}
