package com.anddev.movieguide.movieBranch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.tools.ConnectionInterface;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_movie)
public class MovieActvity extends AppCompatActivity {

    @AfterViews
    public void onCreate() {

    }

    @Background
    void downloadMovieInBackground() {

    }

}
