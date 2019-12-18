package com.anddev.movieguide.tvShow;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.tools.RetrofitTools;

public class TvShowActivity extends AppCompatActivity {

    Activity activity;
    Integer movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow);
        this.activity = this;
        try {
            if (activity.getIntent().getExtras() != null) {
                movieId = activity.getIntent().getExtras().getInt("Id", 0);
                Toast.makeText(activity, "id" + movieId, Toast.LENGTH_SHORT).show();
            } else {
                movieId = RetrofitTools.EXAMPLE_ID_MOVIE;
            }
        } catch (Exception e) {
            movieId = RetrofitTools.EXAMPLE_ID_MOVIE;
        }

    }
}
