package com.anddev.movieguide.moviesActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Genre;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.BackStackManager;
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

    MoviesFragment topRatedMoviesFragment;
    DownloadManager topRatedMoviesDownloadManager;
    Movies topRatedMovies;
    UpdateDownloader topRatedMoviesUpdateDownloader;

    MoviesFragment upcomingMoviesFragment;
    DownloadManager upcomingMoviesDownloadManager;
    Movies upcomingMovies;
    UpdateDownloader upcomingMoviesUpdateDownloader;

    MoviesFragment nowPlayingMoviesFragment;
    DownloadManager nowPlayingMoviesDownloadManager;
    Movies nowPlayingMovies;
    UpdateDownloader nowPlayingMoviesUpdateDownloader;

    @AfterViews
    public void onCreate() {
        activity = this;
        BackStackManager.getInstance().addActivity(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.movies_navigation_draver).setOtherColorForMoviesButton();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(MyApplication.getStringFromResource(R.string.movies));
        StatusBarAndSoftKey.changeColor(this);
        networkChangeReceiver = new NetworkChangeReceiver(this).setOnNetworkChangeReceiver(this);

        client = RetrofitTools.getConnectionInterface();

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager_movies_activity);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);
        actionBarTools.addTabsToViewAndSetListeners(TabsPagerAdapter.getTabs(), viewPager);
        viewPager.addOnPageChangeListener(this);

        popularMoviesDownloadManager = new DownloadManager();
        popularMoviesDownloadManager.setOnDownloadManagerListener(this);
        popularMoviesDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        topRatedMoviesDownloadManager = new DownloadManager();
        topRatedMoviesDownloadManager.setOnDownloadManagerListener(this);
        topRatedMoviesDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        upcomingMoviesDownloadManager = new DownloadManager();
        upcomingMoviesDownloadManager.setOnDownloadManagerListener(this);
        upcomingMoviesDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        nowPlayingMoviesDownloadManager = new DownloadManager();
        nowPlayingMoviesDownloadManager.setOnDownloadManagerListener(this);
        nowPlayingMoviesDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeView(-1);
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
                                    if (popularMoviesUpdateDownloader != null) {
                                        popularMoviesUpdateDownloader.downloadedPage(page);
                                    }

                                }
                            } catch (Exception e) {
                            }
                        } else {
                            popularMovies = response.body();
                            popularMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {
                        if (popularMoviesUpdateDownloader != null) {
                            popularMoviesUpdateDownloader.notDownloadedPage(page);
                        }
                        popularMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    popularMoviesDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                    popularMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    popularMoviesDownloadManager.changeStateDownloadInProgress(false);
                    if (popularMoviesUpdateDownloader != null) {
                        popularMoviesUpdateDownloader.notDownloadedPage(page);
                    }
                }
            });
        } catch (Throwable e) {

            popularMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            popularMoviesDownloadManager.changeStateDownloadInProgress(false);
            if (popularMoviesUpdateDownloader != null) {
                popularMoviesUpdateDownloader.notDownloadedPage(page);
            }
        }

    }

    @Background
    void downloadTopRatedMoviesInBackground(ConnectionInterface client, String apiKey, String
            language, Integer page) {

        try {

            Call<Movies> call = client.topRatedMovies(apiKey, language, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {
                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    topRatedMoviesFragment.addData(response.body());
                                    topRatedMoviesUpdateDownloader.downloadedPage(page);

                                }
                            } catch (Exception e) {
                            }
                        } else {
                            topRatedMovies = response.body();
                            topRatedMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {

                        if (topRatedMoviesUpdateDownloader != null) {
                            topRatedMoviesUpdateDownloader.notDownloadedPage(page);
                        }
                        topRatedMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    topRatedMoviesDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                    topRatedMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    topRatedMoviesDownloadManager.changeStateDownloadInProgress(false);
                    if (topRatedMoviesUpdateDownloader != null) {
                        topRatedMoviesUpdateDownloader.notDownloadedPage(page);
                    }
                }
            });
        } catch (Throwable e) {
            topRatedMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            topRatedMoviesDownloadManager.changeStateDownloadInProgress(false);
            if (topRatedMoviesUpdateDownloader != null) {
                topRatedMoviesUpdateDownloader.notDownloadedPage(page);
            }
        }

    }

    @Background
    void downloadUpcomingMoviesInBackground(ConnectionInterface client, String apiKey, String
            language, Integer page) {

        try {

            Call<Movies> call = client.upcomingMovies(apiKey, language, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {
                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    upcomingMoviesFragment.addData(response.body());
                                    if (upcomingMoviesUpdateDownloader != null) {
                                        upcomingMoviesUpdateDownloader.downloadedPage(page);
                                    }

                                }
                            } catch (Exception e) {
                            }
                        } else {
                            upcomingMovies = response.body();
                            upcomingMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {

                        if (upcomingMoviesUpdateDownloader != null) {
                            upcomingMoviesUpdateDownloader.notDownloadedPage(page);
                        }
                        upcomingMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    upcomingMoviesDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                    upcomingMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    upcomingMoviesDownloadManager.changeStateDownloadInProgress(false);
                    if (upcomingMoviesUpdateDownloader != null) {
                        upcomingMoviesUpdateDownloader.notDownloadedPage(page);
                    }
                }
            });
        } catch (Throwable e) {

            upcomingMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            upcomingMoviesDownloadManager.changeStateDownloadInProgress(false);
            if (upcomingMoviesUpdateDownloader != null) {
                upcomingMoviesUpdateDownloader.notDownloadedPage(page);
            }
        }

    }

    @Background
    void downloadNowPlayingMoviesInBackground(ConnectionInterface client, String apiKey, String
            language, Integer page) {

        try {

            Call<Movies> call = client.nowPlayingMovies(apiKey, language, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {
                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    nowPlayingMoviesFragment.addData(response.body());
                                    if (nowPlayingMoviesUpdateDownloader != null) {
                                        nowPlayingMoviesUpdateDownloader.downloadedPage(page);
                                    }

                                }
                            } catch (Exception e) {
                            }
                        } else {
                            nowPlayingMovies = response.body();
                            nowPlayingMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {

                        if (nowPlayingMoviesUpdateDownloader != null) {
                            nowPlayingMoviesUpdateDownloader.notDownloadedPage(page);
                        }
                        nowPlayingMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    nowPlayingMoviesDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                    nowPlayingMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    nowPlayingMoviesDownloadManager.changeStateDownloadInProgress(false);
                    if (nowPlayingMoviesUpdateDownloader != null) {
                        nowPlayingMoviesUpdateDownloader.notDownloadedPage(page);
                    }
                }
            });
        } catch (Throwable e) {

            nowPlayingMoviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            nowPlayingMoviesDownloadManager.changeStateDownloadInProgress(false);
            if (nowPlayingMoviesUpdateDownloader != null) {
                nowPlayingMoviesUpdateDownloader.notDownloadedPage(page);
            }
        }

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
                        if (topRatedMoviesFragment != null) {
                            topRatedMoviesFragment.setGenres(genres);
                        }
                        if (upcomingMoviesFragment != null) {
                            upcomingMoviesFragment.setGenres(genres);
                        }
                        if (nowPlayingMoviesFragment != null) {
                            nowPlayingMoviesFragment.setGenres(genres);
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
        if (downloadManager == topRatedMoviesDownloadManager) {
            downloadManager.changeStateDownloadInProgress(true);

            try {

                downloadTopRatedMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);

            } catch (Exception e) {

            }
        }
        if (downloadManager == upcomingMoviesDownloadManager) {
            downloadManager.changeStateDownloadInProgress(true);

            try {

                downloadUpcomingMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);

            } catch (Exception e) {

            }
        }
        if (downloadManager == nowPlayingMoviesDownloadManager) {
            downloadManager.changeStateDownloadInProgress(true);

            try {

                downloadNowPlayingMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void downloadPage(UpdateDownloader updateDownloader, int page) {
        if (updateDownloader == popularMoviesUpdateDownloader) {
            downloadPopularMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);
        }
        if (updateDownloader == topRatedMoviesUpdateDownloader) {
            downloadTopRatedMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);
        }
        if (updateDownloader == upcomingMoviesUpdateDownloader) {
            downloadUpcomingMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);
        }
        if (updateDownloader == nowPlayingMoviesUpdateDownloader) {
            downloadNowPlayingMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);
        }
    }

    @Override
    public void showData(DownloadManager downloadManager) {
        if (downloadManager == popularMoviesDownloadManager) {
            if (popularMoviesFragment != null) {
                popularMoviesFragment.setData(popularMovies, genres);
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
            }
        }
        if (downloadManager == topRatedMoviesDownloadManager) {
            if (topRatedMoviesFragment != null) {
                topRatedMoviesFragment.setData(topRatedMovies, genres);
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
            }
        }
        if (downloadManager == upcomingMoviesDownloadManager) {
            if (upcomingMoviesFragment != null) {
                upcomingMoviesFragment.setData(upcomingMovies, genres);
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
            }
        }
        if (downloadManager == nowPlayingMoviesDownloadManager) {
            if (nowPlayingMoviesFragment != null) {
                nowPlayingMoviesFragment.setData(nowPlayingMovies, genres);
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
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
        try {
            topRatedMoviesFragment.setViewType(viewType);
            topRatedMoviesFragment.initializeRecyclerViewAndSetAdapter();

        } catch (Exception e) {
        }
        try {
            upcomingMoviesFragment.setViewType(viewType);
            upcomingMoviesFragment.initializeRecyclerViewAndSetAdapter();

        } catch (Exception e) {
        }
        try {
            nowPlayingMoviesFragment.setViewType(viewType);
            nowPlayingMoviesFragment.initializeRecyclerViewAndSetAdapter();

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
        if (popularMoviesDownloadManager != null) {
            popularMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        }
        if (topRatedMoviesDownloadManager != null) {
            topRatedMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        }
        if (upcomingMoviesDownloadManager != null) {
            upcomingMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        }
        if (nowPlayingMoviesDownloadManager != null) {
            nowPlayingMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        }
    }

    @Override
    public void userTurnedInternetOff() {
        if (popularMoviesDownloadManager != null) {
            popularMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        }
        if (topRatedMoviesDownloadManager != null) {
            topRatedMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        }
        if (upcomingMoviesDownloadManager != null) {
            upcomingMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        }
        if (nowPlayingMoviesDownloadManager != null) {
            nowPlayingMoviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        }
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
                if (genres != null) {
                    popularMoviesFragment.setGenres(genres);
                }
            }

            popularMoviesUpdateDownloader = new UpdateDownloader(popularMoviesFragment.moviesListRecyclerView);
            popularMoviesUpdateDownloader.setOnUpdateDownloaderListener(this);

        }

        if (topRatedMoviesFragment == null && i == 1) {
            String tag = "android:switcher:" + R.id.pager_movies_activity + ":" + 1;
            topRatedMoviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(tag);

            if (topRatedMovies != null) {
                topRatedMoviesDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                topRatedMoviesFragment.setData(topRatedMovies);
                if (genres != null) {
                    topRatedMoviesFragment.setGenres(genres);
                }
            }

            topRatedMoviesUpdateDownloader = new UpdateDownloader(topRatedMoviesFragment.moviesListRecyclerView);
            topRatedMoviesUpdateDownloader.setOnUpdateDownloaderListener(this);

        }

        if (upcomingMoviesFragment == null && i == 2) {
            String tag = "android:switcher:" + R.id.pager_movies_activity + ":" + 2;
            upcomingMoviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(tag);

            if (upcomingMovies != null) {
                upcomingMoviesDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                upcomingMoviesFragment.setData(upcomingMovies);
                if (genres != null) {
                    upcomingMoviesFragment.setGenres(genres);
                }
            }

            upcomingMoviesUpdateDownloader = new UpdateDownloader(upcomingMoviesFragment.moviesListRecyclerView);
            upcomingMoviesUpdateDownloader.setOnUpdateDownloaderListener(this);

        }

        if (nowPlayingMoviesFragment == null && i == 3) {
            String tag = "android:switcher:" + R.id.pager_movies_activity + ":" + 3;
            nowPlayingMoviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(tag);

            if (nowPlayingMovies != null) {
                nowPlayingMoviesDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                nowPlayingMoviesFragment.setData(nowPlayingMovies);
                if (genres != null) {
                    nowPlayingMoviesFragment.setGenres(genres);
                }
            }

            nowPlayingMoviesUpdateDownloader = new UpdateDownloader(nowPlayingMoviesFragment.moviesListRecyclerView);
            nowPlayingMoviesUpdateDownloader.setOnUpdateDownloaderListener(this);

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
