package com.anddev.movieguide.movieBranch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.RetrofitTools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_movie)
public class MovieActvity extends AppCompatActivity {

    @AfterViews
    public void onCreate() {
        ConnectionInterface client = RetrofitTools.getConnectionInterface();
        downloadMovieInBackground(client, RetrofitTools.EXAMPLE_ID_MOVIE, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE);
    }

    @Background
    void downloadMovieInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {


    }

}
