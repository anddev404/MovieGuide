package com.anddev.movieguide.movieBranch;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Actor;
import com.anddev.movieguide.model.Movie;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.DateTools;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.RetrofitTools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_movie)
public class MovieActvity extends AppCompatActivity {

    Activity activity;
    Movie movie;

    @AfterViews
    public void onCreate() {
        activity = this;
        ConnectionInterface client = RetrofitTools.getConnectionInterface();
        downloadMovieInBackground(client, RetrofitTools.EXAMPLE_ID_MOVIE, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE);
    }

    @Background
    void downloadMovieInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {

            Call<Movie> call = client.movie(id, apiKey, language);

            call.enqueue(new Callback<Movie>() {

                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {

                    if (response.code() == 200) {
                        movie = response.body();

                    } else {

                    }

                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                    showError("Brak połączenia internetowego!");

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");
        }

    }

    @UiThread
    public void showError(String message) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }
    
}
