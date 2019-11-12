package com.anddev.movieguide.moviesActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.NavigationBarTools;
import com.anddev.movieguide.tools.RetrofitTools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_movies)
public class MoviesActivity extends AppCompatActivity {

    Activity activity;
    NavigationBarTools navigationDrawer;
    ConnectionInterface client;
    Movies movies;

    @AfterViews
    public void onCreate() {
        activity = this;
        navigationDrawer = new NavigationBarTools(activity);
        try {

            client = RetrofitTools.getConnectionInterface();
            downloadMoviesInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, 1);

        } catch (Exception e) {

        }

    }

    @Background
    void downloadMoviesInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {
        try {


            Call<Movies> call = client.popularMovie(apiKey, language, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {

                    if (response.code() == 200) {

                        movies = response.body();

                    } else {

                        showError("Nie można pobrać odpowiednich danych");

                    }

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

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
