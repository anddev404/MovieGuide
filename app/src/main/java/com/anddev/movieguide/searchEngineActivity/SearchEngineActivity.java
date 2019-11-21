package com.anddev.movieguide.searchEngineActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.RetrofitTools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_search_engine)
public class SearchEngineActivity extends AppCompatActivity {

    Activity activity;
    NavigationDrawerTools navigationDrawer;

    ConnectionInterface client;
    Movies movies;
    PopularPeople people;

    @AfterViews
    public void onCreate() {
        activity = this;
        navigationDrawer = new NavigationDrawerTools(activity);

        client = RetrofitTools.getConnectionInterface();
        downloadMoviesInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, RetrofitTools.EXAMPLE_SEARCH_MOVIE, 1);
        downloadPeopleInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, RetrofitTools.EXAMPLE_SEARCH_PERSON, 1);

    }

    @Background
    void downloadMoviesInBackground(ConnectionInterface client, String apiKey, String language, String query, Integer page) {
        try {


            Call<Movies> call = client.searchMovies(apiKey, language, query, page);

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

    @Background
    void downloadPeopleInBackground(ConnectionInterface client, String apiKey, String language, String query, Integer page) {
        try {


            Call<PopularPeople> call = client.searchPerson(apiKey, language, query, page);

            call.enqueue(new Callback<PopularPeople>() {

                @Override
                public void onResponse(Call<PopularPeople> call, Response<PopularPeople> response) {

                    if (response.code() == 200) {

                        people = response.body();

                    } else {

                        showError("Nie można pobrać odpowiednich danych");

                    }

                }

                @Override
                public void onFailure(Call<PopularPeople> call, Throwable t) {

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
