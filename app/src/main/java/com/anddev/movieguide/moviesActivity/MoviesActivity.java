package com.anddev.movieguide.moviesActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Genre;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.DownloadManager;
import com.anddev.movieguide.tools.InternetTools;
import com.anddev.movieguide.tools.LanguageTools;
import com.anddev.movieguide.tools.MyApplication;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.NetworkChangeReceiver;
import com.anddev.movieguide.tools.RetrofitTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;
import com.anddev.movieguide.tools.UpdateDownloader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_movies)
public class MoviesActivity extends AppCompatActivity implements DownloadManager.OnDownloadManagerListener, NetworkChangeReceiver.onSubmitListener, UpdateDownloader.OnUpdatePageDownloaderListener, ActionBarTools.OnChangeViewListener, ViewPager.OnPageChangeListener {

    Activity activity;

    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;
    NetworkChangeReceiver networkChangeReceiver;
    AlertDialog internetDialog;

    ConnectionInterface client;
    Genre genres;

    ViewPager viewPager;
    TabsPagerAdapter mAdapter;

    MoviesFragment popularMoviesFragment;
    DownloadManager popularMoviesDownloadManager;
    Movies popularMovies;
    UpdateDownloader popularMoviesUpdateDownloader;

    @AfterViews
    public void onCreate() {
        activity = this;
        navigationDrawer = new NavigationDrawerTools(activity, R.id.movies_navigation_draver).setOtherColorForMoviesButton();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(MyApplication.getStringFromResource(R.string.movies));
        StatusBarAndSoftKey.changeColor(this);
        networkChangeReceiver = new NetworkChangeReceiver(this).setOnNetworkChangeReceiver(this);

        client = RetrofitTools.getConnectionInterface();

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager_movies_activity);
        viewPager.setAdapter(mAdapter);
        actionBarTools.addTabsToViewAndSetListeners(TabsPagerAdapter.getTabs(), viewPager);
        viewPager.addOnPageChangeListener(this);

        popularMoviesDownloadManager = new DownloadManager();
        popularMoviesDownloadManager.setOnDownloadManagerListener(this);
        popularMoviesDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));


    }

    @Override
    protected void onPause() {
        super.onPause();
        actionBarTools.closeSearchEngineIfOpen();
        networkChangeReceiver.unregisterNetworkChangeReceiver(activity);

        if (navigationDrawer != null) {
            if (navigationDrawer.closeNavigationDrawerIfOpen()) {
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkChangeReceiver.registerNetworkChangeReceiver();

    }

    @Background
    void downloadPopularMoviesInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {

        try {

            Call<Movies> call = client.popularMovies(apiKey, language, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {
                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    popularMoviesFragment.addData(response.body());
                                    popularMoviesUpdateDownloader.downloadedPage(page);

                                }
                            } catch (Exception e) {
                            }
                        } else {
                            popularMovies = response.body();
                            popularMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {

                        popularMoviesUpdateDownloader.notDownloadedPage(page);
                        popularMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    popularMoviesDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                    //showError("Brak połączenia internetowego!");
                    popularMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    popularMoviesDownloadManager.changeStateDownloadInProgress(false);
                    popularMoviesUpdateDownloader.notDownloadedPage(page);

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");

            popularMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            popularMoviesDownloadManager.changeStateDownloadInProgress(false);
            popularMoviesUpdateDownloader.notDownloadedPage(page);

        }
        // popularMoviesDownloadManager.changeStateInProgress(false);

    }

    @Background
    void downloadGenresInBackground(ConnectionInterface client, String apiKey, String language) {
        try {


            Call<Genre> call = client.genres(apiKey, language);

            call.enqueue(new Callback<Genre>() {

                @Override
                public void onResponse(Call<Genre> call, Response<Genre> response) {

                    if (response.code() == 200) {

                        genres = response.body();

                        if (popularMoviesFragment != null) {
                            popularMoviesFragment.setGenres(genres);
                        }

                    } else {

                    }

                }

                @Override
                public void onFailure(Call<Genre> call, Throwable t) {
                }
            });
        } catch (Throwable e) {
        }

    }

    @UiThread
    public void showError(String message) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void downloadData(DownloadManager downloadManager) {
        if (downloadManager == popularMoviesDownloadManager) {
            downloadManager.changeStateDownloadInProgress(true);

            try {

                downloadPopularMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);
                downloadGenresInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));

            } catch (Exception e) {

            }
        }

    }

    @Override
    public void downloadPage(UpdateDownloader updateDownloader, int page) {
        if (updateDownloader == popularMoviesUpdateDownloader) {
            downloadPopularMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);

        }

    }

    @Override
    public void showData(DownloadManager downloadManager) {
        if (downloadManager == popularMoviesDownloadManager) {
            if (popularMoviesFragment != null) {
                popularMoviesFragment.setData(popularMovies, genres);
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                Log.d("show data", "MARCIN");//filtrowanie w logCat po treści - nie tagu
            }
        }

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (actionBarTools != null) {
            actionBarTools.addSearchEngine(activity, R.menu.action_bar, R.id.app_bar_search, menu,
                    new ActionBarTools.OnSearchEngineListener() {
                        @Override
                        public void onQueryTextSubmit(String query) {
                            SearchEngineActivity.searchAndOpenResults(query, activity);
                        }
                    });

            actionBarTools.addButtonChangeViewAndSetOnClickListener(activity, R.menu.action_bar, menu);
            actionBarTools.setOnChangeViewListener(this::changeView);
        }

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

    @Override
    public void changeView(int viewType) {
        try {
            popularMoviesFragment.setViewType(viewType);
            popularMoviesFragment.initializeRecyclerViewAndSetAdapter();

        } catch (Exception e) {
        }
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

    //region checkInternetConnection

    @Override
    public void userTurnedInternetOn() {
        popularMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
    }

    @Override
    public void userTurnedInternetOff() {
        popularMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
    }

    //endregion

    //region tab view

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        if (popularMoviesFragment == null && i == 0) {
            String tag = "android:switcher:" + R.id.pager_movies_activity + ":" + 0;
            popularMoviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(tag);

            if (popularMovies != null) {
                popularMoviesDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                popularMoviesFragment.setData(popularMovies);
            }

            popularMoviesUpdateDownloader = new UpdateDownloader(popularMoviesFragment.moviesListRecyclerView);
            popularMoviesUpdateDownloader.setOnUpdateDownloaderListener(this);

        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    //endregion
}
