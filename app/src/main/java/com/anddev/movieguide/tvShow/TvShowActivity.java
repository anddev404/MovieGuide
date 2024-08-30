package com.anddev.movieguide.tvShow;

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
import com.anddev.movieguide.model.TvShow;
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.movieActivity.CreditsAdapter;
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


@EActivity(R.layout.activity_tvshow)
public class TvShowActivity extends AppCompatActivity implements DownloadManager.OnDownloadManagerListener, NetworkChangeReceiver.onSubmitListener {

    //region variables
    Activity activity;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;
    NetworkChangeReceiver networkChangeReceiver;

    TvShow tvShow;
    Integer tvShowId;
    Credits credits;
    TvShows similarTvShows;

    DownloadManager downloadManager;
    ConnectionInterface client;
    AlertDialog internetDialog;
    FavouriteTools favouriteTools;

    Integer color;

    @BindView(R.id.tv_show_activity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_show_linear_layout_1)
    LinearLayout linearLayout1;

    @BindView(R.id.tv_show_linear_layout_2)
    LinearLayout linearLayout2;

    @BindView(R.id.tv_show_linear_layout_3)
    LinearLayout linearLayout3;

    @BindView(R.id.poster_tv_show_imageView)
    ImageView poster;

    @BindView(R.id.title_tv_show_textView)
    TextView title;

    @BindView(R.id.oryginal_title_tv_show_textView)
    TextView oryginalTitle;

    @BindView(R.id.overview_tv_show_TextView)
    TextView overview;

    @BindView(R.id.vote_average_tv_show_textView)
    TextView voteAverage;

    @BindView(R.id.release_data_tv_show_textView)
    TextView releaseData;

    @BindView(R.id.genres_tv_show_textView)
    TextView genres;

    @BindView(R.id.production_countries_tv_show_textView)
    TextView productionCountries;

    @BindView(R.id.credits_tv_show_recycler_view)
    RecyclerView creditsRecyclerView;

    @BindView(R.id.similar_tv_show_recycler_view)
    RecyclerView similarTvShowsRecyclerView;

    @BindView(R.id.tv_show_favourite_FloatingActionButton)
    FloatingActionButton favouriteFloatingActionButton;

    @BindView(R.id.trainers_tv_show_activity_button)
    Button trainersButton;

    @BindView(R.id.youtube_tv_show)
    ImageView youtubeTrainersButton;
    // endregion

    //region activity
    @AfterViews
    public void onCreate() {
        this.activity = this;
        BackStackManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.tv_show_navigation_draver).setNormalColorForAllButtons();
        actionBarTools = new ActionBarTools(this, toolbar).addMenuButton().setTitle("");
        StatusBarAndSoftKey.changeColor(this);
        networkChangeReceiver = new NetworkChangeReceiver(this).setOnNetworkChangeReceiver(this);

        try {
            if (activity.getIntent().getExtras() != null) {
                tvShowId = activity.getIntent().getExtras().getInt("Id", 0);
            } else {
                tvShowId = RetrofitTools.EXAMPLE_ID_MOVIE;
            }
        } catch (Exception e) {
            tvShowId = RetrofitTools.EXAMPLE_ID_MOVIE;
        }

        try {
            if (activity.getIntent().getExtras() != null) {
                color = activity.getIntent().getExtras().getInt("color", 0);
            }
        } catch (Exception e) {
        }

        client = RetrofitTools.getConnectionInterface();

        creditsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similarTvShowsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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

    @OnClick(R.id.trainers_tv_show_activity_button)
    public void onTrainersClick() {

        try {

            TrailersActivity.goToActivity(this, tvShow.getName() + " " + DateTools.getOnlyYear(tvShow.getFirst_air_date()));

        } catch (Exception e) {
            try {

                TrailersActivity.goToActivity(this, tvShow.getName());

            } catch (Exception ee) {

                Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @OnClick(R.id.youtube_tv_show)
    public void onYouTubeTrainersClick() {

        try {

            TrailersActivity.goToActivity(this, tvShow.getName() + " " + DateTools.getOnlyYear(tvShow.getFirst_air_date()));

        } catch (Exception e) {
            try {

                TrailersActivity.goToActivity(this, tvShow.getName());

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
    void downloadTvShowInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {

            Call<TvShow> call = client.tvShow(id, apiKey, language);

            call.enqueue(new Callback<TvShow>() {

                @Override
                public void onResponse(Call<TvShow> call, Response<TvShow> response) {

                    if (response.code() == 200) {
                        tvShow = response.body();
                        ImageTools.getWideImageFromInternet(activity, ImageTools.IMAGE_PATH_ORYGINAL + tvShow.getBackdrop_path(), poster, ImageTools.DRAWABLE_FILM_WIDTH);
                        downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);

                    }
                    downloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<TvShow> call, Throwable t) {

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


            Call<Credits> call = client.tvShowCredits(id, apiKey);

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

            Call<TvShows> call = client.similarTvShows(id, apiKey, language, page);

            call.enqueue(new Callback<TvShows>() {

                @Override
                public void onResponse(Call<TvShows> call, Response<TvShows> response) {

                    if (response.code() == 200) {

                        similarTvShows = response.body();
                        showSimilar(similarTvShows);

                    } else {

                    }

                }

                @Override
                public void onFailure(Call<TvShows> call, Throwable t) {

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
    public void showDataOfTvShow(TvShow tvShow) {

        title.setText(tvShow.getName());
        oryginalTitle.setText(tvShow.getOriginal_name());
        overview.setText(tvShow.getOverview());
        voteAverage.setText(Double.toString(tvShow.getVote_average()));
        releaseData.setText(tvShow.getFirst_air_date());
        genres.setText(tvShow.genresToString());
        productionCountries.setText(tvShow.productionCompaniesToString());

        try {
            favouriteTools.manageFavouriteButton(favouriteFloatingActionButton, tvShowId, Favourite.FAVOURITE_TV_SHOWS, tvShow.getName() + " " + DateTools.getOnlyYear(tvShow.getFirst_air_date()), "", Double.toString(tvShow.getVote_average()), tvShow.getPoster_path());
        } catch (Exception e) {
            favouriteTools.manageFavouriteButton(favouriteFloatingActionButton, tvShowId, Favourite.FAVOURITE_TV_SHOWS, "id: " + tvShowId, "", "", "");
        }
    }

    @UiThread
    public void showCredits(final Credits credits) {
        try {

            CreditsAdapter adapter = new CreditsAdapter(this, credits.getCast());
            creditsRecyclerView.setAdapter(adapter);

//            creditsRecyclerView.addOnItemTouchListener(
//                    new RecyclerItemClickListener(activity, creditsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//                            Intent intent = new Intent(activity, ActorActivity_.class);
//                            intent.putExtra("Id", credits.getCast().get(position).getId());
//                            startActivity(intent);
//                        }
//
//                        @Override
//                        public void onLongItemClick(View view, int position) {
//                        }
//
//                    })
//            );

        } catch (Exception e) {
        }
    }

    @UiThread
    public void showSimilar(final TvShows tvShows) {
        try {

            KnownForAdapter adapter = new KnownForAdapter(this, ModelConverter.tvShowsToListOfCast(tvShows));
            similarTvShowsRecyclerView.setAdapter(adapter);

        } catch (Exception e) {
        }
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

        downloadTvShowInBackground(client, tvShowId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));

        if (credits == null) {
            downloadCreditsInBackground(client, tvShowId, RetrofitTools.API_KEY);
        }
        if (similarTvShows == null) {
            downloadSimilarInBackground(client, tvShowId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);
        }

    }

    @Override
    public void showData(DownloadManager downloadManager) {
        downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
        showDataOfTvShow(tvShow);

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
