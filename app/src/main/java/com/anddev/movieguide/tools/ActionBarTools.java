package com.anddev.movieguide.tools;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.moviesActivity.MoviesActivity;

import butterknife.ButterKnife;

public class ActionBarTools {

    AppCompatActivity activity;
    ActionBarTools actionBarTools;

    public ActionBarTools(AppCompatActivity activity) {

        this.activity = activity;
        actionBarTools = this;
    }

    public ActionBarTools addMenuButton() {

        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_button);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return actionBarTools;

    }


}
