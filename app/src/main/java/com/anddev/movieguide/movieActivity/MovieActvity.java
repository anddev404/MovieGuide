package com.anddev.movieguide.movieActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movie;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.RetrofitTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_movie)
public class MovieActvity extends AppCompatActivity {

    Activity activity;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

    Movie movie;
    Integer movieId;


    @BindView(R.id.poster_movie_imageView)
    ImageView poster;

    @BindView(R.id.title_movie_textView)
    TextView title;

    @BindView(R.id.oryginal_title_movie_textView)
    TextView oryginalTitle;

    @BindView(R.id.overview_movie_TextView)
    TextView overview;

    @BindView(R.id.vote_average_movie_textView)
    TextView voteAverage;

    @BindView(R.id.release_data_movie_textView)
    TextView releaseData;

    @BindView(R.id.genres_movie_textView)
    TextView genres;

    @BindView(R.id.runtime_movie_textView)
    TextView runtime;

    @BindView(R.id.production_countries_movie_textView)
    TextView productionCountries;

    @AfterViews
    public void onCreate() {
        activity = this;
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.movie_navigation_draver).setNormalColorForAllButtons();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle("Movie");
        StatusBarAndSoftKey.changeColor(this);
        try {
            if (activity.getIntent().getExtras() != null) {
                movieId = activity.getIntent().getExtras().getInt("Id", 0);
            } else {
                movieId = RetrofitTools.EXAMPLE_ID_MOVIE;
            }
        } catch (Exception e) {
            movieId = RetrofitTools.EXAMPLE_ID_MOVIE;
        }
        ConnectionInterface client = RetrofitTools.getConnectionInterface();
        downloadMovieInBackground(client, movieId, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (navigationDrawer != null) {
            if (navigationDrawer.closeNavigationDrawerIfOpen()) {
            }
        }
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
                        ImageTools.getImageFromInternet(activity, ImageTools.IMAGE_PATH_ORYGINAL + movie.getBackdrop_path(), poster, ImageTools.DRAWABLE_FILM);
                        showDataOfMovie(movie);

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

    @UiThread
    public void showDataOfMovie(Movie movie) {

        title.setText(movie.getTitle());
        oryginalTitle.setText(movie.getOriginal_title());
        overview.setText(movie.getOverview());
        voteAverage.setText(Double.toString(movie.getVote_average()) + "/10");
        releaseData.setText(movie.getRelease_date());
        genres.setText(movie.genresToString());
        runtime.setText(movie.getRuntime() + " min.");
        productionCountries.setText(movie.productionCountriesToString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchEngineActivity.searchAndOpenResults(query, activity);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:

                navigationDrawer.openOrCloseNavigationDrawer();

                break;

        }

        return true;

    }

    //region back Button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if (navigationDrawer != null) {
                if (navigationDrawer.closeNavigationDrawerIfOpen()) {
                    return true;
                }

            }

            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }
    //endregion
}
