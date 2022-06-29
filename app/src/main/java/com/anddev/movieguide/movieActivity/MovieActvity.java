package com.anddev.movieguide.movieActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.actorActivity.KnownForAdapter;
import com.anddev.movieguide.model.Credits;
import com.anddev.movieguide.model.Favourite;
import com.anddev.movieguide.model.Movie;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.BackStackManager;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.DateTools;
import com.anddev.movieguide.tools.DownloadManager;
import com.anddev.movieguide.tools.FavouriteTools;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.InternetTools;
import com.anddev.movieguide.tools.LanguageTools;
import com.anddev.movieguide.tools.ModelConverter;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.NetworkChangeReceiver;
import com.anddev.movieguide.tools.RetrofitTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;
import com.anddev.movieguide.trailersActivity.TrailersActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

@EActivity(R.layout.activity_movie)
public class MovieActvity extends AppCompatActivity implements DownloadManager.OnDownloadManagerListener, NetworkChangeReceiver.onSubmitListener {

    //region variables
    Activity activity;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;
    NetworkChangeReceiver networkChangeReceiver;

    Movie movie;
    Integer movieId;
    Credits credits;
    Movies similarMovies;

    DownloadManager downloadManager;
    ConnectionInterface client;
    AlertDialog internetDialog;
    FavouriteTools favouriteTools;

    Integer color;

    @BindView(R.id.movie_activity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.movie_linear_layout_1)
    LinearLayout linearLayout1;

    @BindView(R.id.movie_linear_layout_2)
    LinearLayout linearLayout2;

    @BindView(R.id.movie_linear_layout_3)
    LinearLayout linearLayout3;

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

    @BindView(R.id.credits_movie_recycler_view)
    RecyclerView creditsRecyclerView;

    @BindView(R.id.similar_movies_movie_recycler_view)
    RecyclerView similarRecyclerView;

    @BindView(R.id.movie_favourite_FloatingActionButton)
    FloatingActionButton favouriteFloatingActionButton;

    @BindView(R.id.trainers_movie_activity_button)
    Button trainersButton;

    @BindView(R.id.youtube_movie)
    ImageView youtubeTrainersButton;
    // endregion

    //region activity
    @AfterViews
    public void onCreate() {
        activity = this;
        BackStackManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.movie_navigation_draver).setNormalColorForAllButtons();
        actionBarTools = new ActionBarTools(this, toolbar).addMenuButton().setTitle("");
        StatusBarAndSoftKey.changeColor(this);
        networkChangeReceiver = new NetworkChangeReceiver(this).setOnNetworkChangeReceiver(this);

        try {
            if (activity.getIntent().getExtras() != null) {
                movieId = activity.getIntent().getExtras().getInt("Id", 0);
            } else {
                movieId = RetrofitTools.EXAMPLE_ID_MOVIE;
            }
        } catch (Exception e) {
            movieId = RetrofitTools.EXAMPLE_ID_MOVIE;
        }
        try {
            if (activity.getIntent().getExtras() != null) {
                color = activity.getIntent().getExtras().getInt("color", 0);
            }
        } catch (Exception e) {
        }

        client = RetrofitTools.getConnectionInterface();

        creditsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similarRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        downloadManager = new DownloadManager();
        downloadManager.setOnDownloadManagerListener(this);
        downloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        favouriteTools = new FavouriteTools(activity);
        changeViewColor();
        rotateLinearLayoutBaseOnTheOrientation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkChangeReceiver.registerNetworkChangeReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        networkChangeReceiver.unregisterNetworkChangeReceiver(activity);
        actionBarTools.closeSearchEngineIfOpen();

        if (navigationDrawer != null) {
            if (navigationDrawer.closeNavigationDrawerIfOpen()) {
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        rotateLinearLayoutBaseOnTheOrientation();
    }

    public void rotateLinearLayoutBaseOnTheOrientation() {
        int orientation = activity.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
        }
    }

    @OnClick(R.id.trainers_movie_activity_button)
    public void onTrainersClick() {

        try {

            TrailersActivity.goToActivity(this, movie.getTitle() + " " + DateTools.getOnlyYear(movie.getRelease_date()));

        } catch (Exception e) {
            try {

                TrailersActivity.goToActivity(this, movie.getTitle());

            } catch (Exception ee) {

                Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.youtube_movie)
    public void onYoutubeTrainersClick() {

        try {

            TrailersActivity.goToActivity(this, movie.getTitle() + " " + DateTools.getOnlyYear(movie.getRelease_date()));

        } catch (Exception e) {
            try {

                TrailersActivity.goToActivity(this, movie.getTitle());

            } catch (Exception ee) {

                Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //endregion
    void changeViewColor() {
        try {
            if (color != 0) {

                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(true);

                linearLayout1.setBackgroundColor(color);
                linearLayout1.getBackground().setAlpha(120);

                linearLayout2.setBackgroundColor(color);
                linearLayout2.getBackground().setAlpha(180);

                linearLayout3.setBackgroundColor(color);
                linearLayout3.getBackground().setAlpha(60);

                favouriteFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));

                StatusBarAndSoftKey.changeColor(activity, color);
            }
        } catch (Exception e) {
        }
    }

    //region download
    @Background
    void downloadMovieInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {

            Call<Movie> call = client.movie(id, apiKey, language);

            call.enqueue(new Callback<Movie>() {

                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {

                    if (response.code() == 200) {
                        movie = response.body();
                        ImageTools.getWideImageFromInternet(activity, ImageTools.IMAGE_PATH_ORYGINAL + movie.getBackdrop_path(), poster, ImageTools.DRAWABLE_FILM_WIDTH);
                        downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        downloadManager.changeStateDownloadInProgress(false);
                    }


                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                    downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    downloadManager.changeStateDownloadInProgress(false);

                }
            });
        } catch (Throwable e) {

            downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            downloadManager.changeStateDownloadInProgress(false);

        }

    }

    @Background
    void downloadCreditsInBackground(ConnectionInterface client, Integer id, String apiKey) {
        try {


            Call<Credits> call = client.credits(id, apiKey);

            call.enqueue(new Callback<Credits>() {

                @Override
                public void onResponse(Call<Credits> call, Response<Credits> response) {

                    if (response.code() == 200) {

                        credits = response.body();
                        showCredits(credits);

                    } else {
                    }

                }

                @Override
                public void onFailure(Call<Credits> call, Throwable t) {
                }
            });
        } catch (Throwable e) {
        }

    }

    @Background
    void downloadSimilarInBackground(ConnectionInterface client, Integer id, String apiKey, String language, Integer page) {
        try {


            Call<Movies> call = client.similarMovies(id, apiKey, language, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {

                    if (response.code() == 200) {

                        similarMovies = response.body();
                        showSimilar(similarMovies);

                    } else {
                    }

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {
                }
            });
        } catch (Throwable e) {
        }

    }
    //endregion

    public String getPercentageFromDouble(Double d, int scale) {
        try {

            Double result = d / scale;
            result = result * 100;
            String s = "" + result.intValue() + "%";
            return s;

        } catch (Exception e) {
            return "";
        }
    }

    @UiThread
    public void showDataOfMovie(Movie movie) {
        title.setText(movie.getTitle());
        oryginalTitle.setText(movie.getOriginal_title());
        overview.setText(movie.getOverview());
        voteAverage.setText(getPercentageFromDouble(movie.getVote_average(), 10));
        releaseData.setText(movie.getRelease_date());
        genres.setText(movie.genresToString());
        runtime.setText(movie.getRuntime() + " min.");
        productionCountries.setText(movie.productionCountriesToString());

        try {
            favouriteTools.manageFavouriteButton(favouriteFloatingActionButton, movieId, Favourite.FAVOURITE_MOVIE, movie.getTitle() + " " + DateTools.getOnlyYear(movie.getRelease_date()), "", Double.toString(movie.getVote_average()), movie.getPoster_path());
        } catch (Exception e) {
            favouriteTools.manageFavouriteButton(favouriteFloatingActionButton, movieId, Favourite.FAVOURITE_MOVIE, "id: " + movieId, "", "", "");
        }
    }

    @UiThread
    public void showCredits(final Credits credits) {

        CreditsAdapter adapter = new CreditsAdapter(this, credits.getCast());
        creditsRecyclerView.setAdapter(adapter);

//        creditsRecyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(activity, creditsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Intent intent = new Intent(activity, ActorActivity_.class);
//                        intent.putExtra("Id", credits.getCast().get(position).getId());
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                    }
//
//                })
//        );

    }

    @UiThread
    public void showSimilar(final Movies movies) {

        KnownForAdapter adapter = new KnownForAdapter(this, ModelConverter.moviesToListOfCast(movies));
        similarRecyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        actionBarTools.addSearchEngine(activity, R.menu.action_bar, R.id.app_bar_search, menu,
                new ActionBarTools.OnSearchEngineListener() {
                    @Override
                    public void onQueryTextSubmit(String query) {
                        SearchEngineActivity.searchAndOpenResults(query, activity);
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

    //region download manager
    @Override
    public void downloadData(DownloadManager downloadManager) {

        downloadManager.changeStateDownloadInProgress(true);

        downloadMovieInBackground(client, movieId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));

        if (credits == null) {
            downloadCreditsInBackground(client, movieId, RetrofitTools.API_KEY);
        }
        if (similarMovies == null) {
            downloadSimilarInBackground(client, movieId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);
        }

    }

    @Override
    public void showData(DownloadManager downloadManager) {
        downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
        showDataOfMovie(movie);

    }

    @Override
    public void showNoInternetNotification(DownloadManager downloadManager) {
        downloadManager.changeStateNotificationIsShowing(DownloadManager.NOTIFICATION_IS_SHOWING);

        if (internetDialog != null) {

            internetDialog.show();

        } else {
            internetDialog = InternetTools.showNoConnectionDialog(activity);

        }
    }

    @Override
    public void hideNoInternetNotification(DownloadManager downloadManager) {
        downloadManager.changeStateNotificationIsShowing(DownloadManager.NOTIFICATION_IS_NOT_SHOWING);

        if (internetDialog != null) {

            internetDialog.dismiss();

        }
    }
    //endregion

    //region checkInternetConnection

    @Override
    public void userTurnedInternetOn() {
        downloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
    }

    @Override
    public void userTurnedInternetOff() {
        downloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
    }

    //endregion
}
