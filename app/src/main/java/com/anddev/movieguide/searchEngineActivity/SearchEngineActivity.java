package com.anddev.movieguide.searchEngineActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.moviesActivity.MoviesFragment;
import com.anddev.movieguide.peopleActivity.PeopleFragment;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.RetrofitTools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_search_engine)
public class SearchEngineActivity extends AppCompatActivity {

    Activity activity;
    MoviesFragment moviesFragment;
    MoviesFragment tvShowsFragment;
    PeopleFragment peopleFragment;

    NavigationDrawerTools navigationDrawer;

    ConnectionInterface client;
    Movies movies;
    Movies tvShows;
    PopularPeople people;


    @AfterViews
    public void onCreate() {
        activity = this;
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity);
        moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_search_movies);
        tvShowsFragment = (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_search_tv_shows);
        peopleFragment = (PeopleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_search_people);

        client = RetrofitTools.getConnectionInterface();

        try {
            if (activity.getIntent().getExtras() != null) {
                String query = activity.getIntent().getExtras().getString("Query", "");
                downloadMoviesInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, query, 1);
                downloadTvShowsInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, query, 1);
                downloadPeopleInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, query, 1);
            } else {
            }
        } catch (Exception e) {
        }

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
                        moviesFragment.setData(movies);

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
    void downloadTvShowsInBackground(ConnectionInterface client, String apiKey, String language, String query, Integer page) {
        try {


            Call<Movies> call = client.searchTvShows(apiKey, language, query, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {

                    if (response.code() == 200) {

                        tvShows = response.body();
                        tvShowsFragment.setData(tvShows);

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
                        peopleFragment.setData(people);

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

    public static void searchAndOpenResults(String query, Activity activity) {
        Intent intent = new Intent(activity, SearchEngineActivity_.class);
        intent.putExtra("Query", query);
        activity.startActivity(intent);
    }
}
