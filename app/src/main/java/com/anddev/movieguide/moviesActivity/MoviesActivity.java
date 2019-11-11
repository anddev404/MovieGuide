package com.anddev.movieguide.moviesActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anddev.movieguide.R;
import com.anddev.movieguide.movieActivity.MovieActvity_;

public class MoviesActivity extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        activity = this;

//        Intent intent = new Intent(activity, MovieActvity_.class);
//        activity.startActivity(intent);
    }
}
