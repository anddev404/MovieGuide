package com.anddev.movieguide.tvShows;

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
import com.anddev.movieguide.model.TvShows;
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

@EActivity(R.layout.activity_tv_shows)
public class TvShowsActivity extends AppCompatActivity implements DownloadManager.OnDownloadManagerListener, NetworkChangeReceiver.onSubmitListener, UpdateDownloader.OnUpdatePageDownloaderListener, ActionBarTools.OnChangeViewListener, ViewPager.OnPageChangeListener {

    Activity activity;

    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;
    NetworkChangeReceiver networkChangeReceiver;
    AlertDialog internetDialog;

    ConnectionInterface client;
    Genre genres;

    ViewPager viewPager;
    TabPagerAdapter mAdapter;

    TvShowsFragment popularTvShowsFragment;
    DownloadManager popularTvShowsDownloadManager;
    TvShows popularTvShows;
    UpdateDownloader popularTvShowsUpdateDownloader;

    TvShowsFragment topRatedTvShowsFragment;
    DownloadManager topRatedTvShowsDownloadManager;
    TvShows topRatedTvShows;
    UpdateDownloader topRatedTvShowsUpdateDownloader;

    TvShowsFragment airingTodayTvShowsFragment;
    DownloadManager airingTodayTvShowsDownloadManager;
    TvShows airingTodayTvShows;
    UpdateDownloader airingTodayTvShowsUpdateDownloader;

    TvShowsFragment onTheAirTvShowsFragment;
    DownloadManager onTheAirTvShowsDownloadManager;
    TvShows onTheAirTvShows;
    UpdateDownloader onTheAirTvShowsUpdateDownloader;

    @AfterViews
    public void onCreate() {
        activity = this;
        BackStackManager.getInstance().addActivity(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.tv_shows_navigation_draver).setOtherColorForTvShowsButton();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(MyApplication.getStringFromResource(R.string.tv_shows));
        StatusBarAndSoftKey.changeColor(this);
        networkChangeReceiver = new NetworkChangeReceiver(this).setOnNetworkChangeReceiver(this);

        client = RetrofitTools.getConnectionInterface();

        mAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager_tv_shows_activity);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);
        actionBarTools.addTabsToViewAndSetListeners(TabPagerAdapter.getTabs(), viewPager);
        viewPager.addOnPageChangeListener(this);

        popularTvShowsDownloadManager = new DownloadManager();
        popularTvShowsDownloadManager.setOnDownloadManagerListener(this);
        popularTvShowsDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        topRatedTvShowsDownloadManager = new DownloadManager();
        topRatedTvShowsDownloadManager.setOnDownloadManagerListener(this);
        topRatedTvShowsDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        airingTodayTvShowsDownloadManager = new DownloadManager();
        airingTodayTvShowsDownloadManager.setOnDownloadManagerListener(this);
        airingTodayTvShowsDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        onTheAirTvShowsDownloadManager = new DownloadManager();
        onTheAirTvShowsDownloadManager.setOnDownloadManagerListener(this);
        onTheAirTvShowsDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

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
    void downloadPopularTvShowsInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {

        try {

            Call<TvShows> call = client.popularTvShows(apiKey, language, page);

            call.enqueue(new Callback<TvShows>() {

                @Override
                public void onResponse(Call<TvShows> call, Response<TvShows> response) {
                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    popularTvShowsFragment.addData(response.body());
                                    if (popularTvShowsUpdateDownloader != null) {
                                        popularTvShowsUpdateDownloader.downloadedPage(page);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        } else {
                            popularTvShows = response.body();
                            popularTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {

                        if (popularTvShowsUpdateDownloader != null) {
                            popularTvShowsUpdateDownloader.notDownloadedPage(page);
                        }
                        popularTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    popularTvShowsDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<TvShows> call, Throwable t) {

                    popularTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    popularTvShowsDownloadManager.changeStateDownloadInProgress(false);
                    if (popularTvShowsUpdateDownloader != null) {
                        popularTvShowsUpdateDownloader.notDownloadedPage(page);
                    }
                }
            });
        } catch (Throwable e) {

            popularTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            popularTvShowsDownloadManager.changeStateDownloadInProgress(false);
            if (popularTvShowsUpdateDownloader != null) {
                popularTvShowsUpdateDownloader.notDownloadedPage(page);
            }

        }

    }

    @Background
    void downloadTopRatedTvShowsInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {

        try {

            Call<TvShows> call = client.topRatedTvShows(apiKey, language, page);

            call.enqueue(new Callback<TvShows>() {

                @Override
                public void onResponse(Call<TvShows> call, Response<TvShows> response) {
                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    topRatedTvShowsFragment.addData(response.body());
                                    if (topRatedTvShowsUpdateDownloader != null) {
                                        topRatedTvShowsUpdateDownloader.downloadedPage(page);
                                    }

                                }
                            } catch (Exception e) {
                            }
                        } else {
                            topRatedTvShows = response.body();
                            topRatedTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {
                        if (topRatedTvShowsUpdateDownloader != null) {
                            topRatedTvShowsUpdateDownloader.notDownloadedPage(page);
                        }
                        topRatedTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    topRatedTvShowsDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<TvShows> call, Throwable t) {

                    topRatedTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    topRatedTvShowsDownloadManager.changeStateDownloadInProgress(false);
                    if (topRatedTvShowsUpdateDownloader != null) {
                        topRatedTvShowsUpdateDownloader.notDownloadedPage(page);
                    }
                }
            });
        } catch (Throwable e) {

            topRatedTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            topRatedTvShowsDownloadManager.changeStateDownloadInProgress(false);
            if (topRatedTvShowsUpdateDownloader != null) {
                topRatedTvShowsUpdateDownloader.notDownloadedPage(page);
            }
        }

    }

    @Background
    void downloadOnTheAirTvShowsInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {

        try {

            Call<TvShows> call = client.onTheAirTvShows(apiKey, language, page);

            call.enqueue(new Callback<TvShows>() {

                @Override
                public void onResponse(Call<TvShows> call, Response<TvShows> response) {
                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    onTheAirTvShowsFragment.addData(response.body());
                                    if (onTheAirTvShowsUpdateDownloader != null) {
                                        onTheAirTvShowsUpdateDownloader.downloadedPage(page);
                                    }

                                }
                            } catch (Exception e) {
                            }
                        } else {
                            onTheAirTvShows = response.body();
                            onTheAirTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {

                        if (onTheAirTvShowsUpdateDownloader != null) {
                            onTheAirTvShowsUpdateDownloader.notDownloadedPage(page);
                        }
                        onTheAirTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    onTheAirTvShowsDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<TvShows> call, Throwable t) {

                    onTheAirTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    onTheAirTvShowsDownloadManager.changeStateDownloadInProgress(false);
                    if (onTheAirTvShowsUpdateDownloader != null) {
                        onTheAirTvShowsUpdateDownloader.notDownloadedPage(page);
                    }
                }
            });
        } catch (Throwable e) {

            onTheAirTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            onTheAirTvShowsDownloadManager.changeStateDownloadInProgress(false);
            if (onTheAirTvShowsUpdateDownloader != null) {
                onTheAirTvShowsUpdateDownloader.notDownloadedPage(page);
            }
        }

    }

    @Background
    void downloadAiringTodayTvShowsInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {

        try {

            Call<TvShows> call = client.airingTodayTvShows(apiKey, language, page);

            call.enqueue(new Callback<TvShows>() {

                @Override
                public void onResponse(Call<TvShows> call, Response<TvShows> response) {
                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    airingTodayTvShowsFragment.addData(response.body());
                                    if (airingTodayTvShowsUpdateDownloader != null) {
                                        airingTodayTvShowsUpdateDownloader.downloadedPage(page);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        } else {
                            airingTodayTvShows = response.body();
                            airingTodayTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                    } else {

                        if (airingTodayTvShowsUpdateDownloader != null) {
                            airingTodayTvShowsUpdateDownloader.notDownloadedPage(page);
                        }
                        airingTodayTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }
                    airingTodayTvShowsDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<TvShows> call, Throwable t) {

                    airingTodayTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    airingTodayTvShowsDownloadManager.changeStateDownloadInProgress(false);
                    if (airingTodayTvShowsUpdateDownloader != null) {
                        airingTodayTvShowsUpdateDownloader.notDownloadedPage(page);
                    }
                }
            });
        } catch (Throwable e) {

            airingTodayTvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            airingTodayTvShowsDownloadManager.changeStateDownloadInProgress(false);
            if (airingTodayTvShowsUpdateDownloader != null) {
                airingTodayTvShowsUpdateDownloader.notDownloadedPage(page);
            }
        }

    }

//    @Background
//    void downloadGenresInBackground(ConnectionInterface client, String apiKey, String language) {
//        try {
//
//
//            Call<Genre> call = client.genres(apiKey, language);
//
//            call.enqueue(new Callback<Genre>() {
//
//                @Override
//                public void onResponse(Call<Genre> call, Response<Genre> response) {
//
//                    if (response.code() == 200) {
//
//                        genres = response.body();
//
//                        if (popularTvShowsFragment != null) {
//                            popularTvShowsFragment.setGenres(genres);
//                        }
//                        if (topRatedTvShowsFragment != null) {
//                            topRatedTvShowsFragment.setGenres(genres);
//                        }
//                        if (airingTodayTvShowsFragment != null) {
//                            airingTodayTvShowsFragment.setGenres(genres);
//                        }
//                        if (airingTodayTvShowsFragment != null) {
//                            airingTodayTvShowsFragment.setGenres(genres);
//                        }
//
//                    } else {
//
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<Genre> call, Throwable t) {
//                }
//            });
//        } catch (Throwable e) {
//        }
//
//    }

    @Override
    public void downloadData(DownloadManager downloadManager) {
        if (downloadManager == popularTvShowsDownloadManager) {
            downloadManager.changeStateDownloadInProgress(true);

            try {

                downloadPopularTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);
//                downloadGenresInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));

            } catch (Exception e) {

            }
        }
        if (downloadManager == topRatedTvShowsDownloadManager) {
            downloadManager.changeStateDownloadInProgress(true);

            try {

                downloadTopRatedTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);

            } catch (Exception e) {

            }
        }
        if (downloadManager == onTheAirTvShowsDownloadManager) {
            downloadManager.changeStateDownloadInProgress(true);

            try {

                downloadOnTheAirTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);

            } catch (Exception e) {

            }
        }
        if (downloadManager == airingTodayTvShowsDownloadManager) {
            downloadManager.changeStateDownloadInProgress(true);

            try {

                downloadAiringTodayTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void downloadPage(UpdateDownloader updateDownloader, int page) {
        if (updateDownloader == popularTvShowsUpdateDownloader) {
            downloadPopularTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);
        }
        if (updateDownloader == topRatedTvShowsUpdateDownloader) {
            downloadTopRatedTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);
        }
        if (updateDownloader == onTheAirTvShowsUpdateDownloader) {
            downloadOnTheAirTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);
        }
        if (updateDownloader == airingTodayTvShowsUpdateDownloader) {
            downloadAiringTodayTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);
        }
    }

    @Override
    public void showData(DownloadManager downloadManager) {
        if (downloadManager == popularTvShowsDownloadManager) {
            if (popularTvShowsFragment != null) {
//                popularTvShowsFragment.setData(popularTvShows, genres);
                popularTvShowsFragment.setData(popularTvShows);
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
            }
        }
        if (downloadManager == topRatedTvShowsDownloadManager) {
            if (topRatedTvShowsFragment != null) {
//                topRatedTvShowsFragment.setData(topRatedTvShows, genres);
                topRatedTvShowsFragment.setData(topRatedTvShows);
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
            }
        }
        if (downloadManager == onTheAirTvShowsDownloadManager) {
            if (onTheAirTvShowsFragment != null) {
//                onTheAirTvShowsFragment.setData(onTheAirTvShows, genres);
                onTheAirTvShowsFragment.setData(onTheAirTvShows);
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
            }
        }
        if (downloadManager == airingTodayTvShowsDownloadManager) {
            if (airingTodayTvShowsFragment != null) {
//                airingTodayTvShowsFragment.setData(airingTodayTvShows, genres);
                airingTodayTvShowsFragment.setData(airingTodayTvShows);
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
            popularTvShowsFragment.setViewType(viewType);
            popularTvShowsFragment.initializeRecyclerViewAndSetAdapter();

        } catch (Exception e) {
        }
        try {
            topRatedTvShowsFragment.setViewType(viewType);
            topRatedTvShowsFragment.initializeRecyclerViewAndSetAdapter();

        } catch (Exception e) {
        }
        try {
            onTheAirTvShowsFragment.setViewType(viewType);
            onTheAirTvShowsFragment.initializeRecyclerViewAndSetAdapter();

        } catch (Exception e) {
        }
        try {
            airingTodayTvShowsFragment.setViewType(viewType);
            airingTodayTvShowsFragment.initializeRecyclerViewAndSetAdapter();

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
        if (popularTvShowsDownloadManager != null) {
            popularTvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        }
        if (topRatedTvShowsDownloadManager != null) {
            topRatedTvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        }
        if (onTheAirTvShowsDownloadManager != null) {
            onTheAirTvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        }
        if (airingTodayTvShowsDownloadManager != null) {
            airingTodayTvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        }
    }

    @Override
    public void userTurnedInternetOff() {
        if (popularTvShowsDownloadManager != null) {
            popularTvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        }
        if (topRatedTvShowsDownloadManager != null) {
            topRatedTvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        }
        if (onTheAirTvShowsDownloadManager != null) {
            onTheAirTvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        }
        if (airingTodayTvShowsDownloadManager != null) {
            airingTodayTvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        }
    }

    //endregion

    //region tab view

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        if (popularTvShowsFragment == null && i == 0) {
            String tag = "android:switcher:" + R.id.pager_tv_shows_activity + ":" + 0;
            popularTvShowsFragment = (TvShowsFragment) getSupportFragmentManager().findFragmentByTag(tag);

            if (popularTvShows != null) {
                popularTvShowsDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                popularTvShowsFragment.setData(popularTvShows);
            }

            popularTvShowsUpdateDownloader = new UpdateDownloader(popularTvShowsFragment.tvShowsListRecyclerView);
            popularTvShowsUpdateDownloader.setOnUpdateDownloaderListener(this);

        }

        if (topRatedTvShowsFragment == null && i == 1) {
            String tag = "android:switcher:" + R.id.pager_tv_shows_activity + ":" + 1;
            topRatedTvShowsFragment = (TvShowsFragment) getSupportFragmentManager().findFragmentByTag(tag);

            if (topRatedTvShows != null) {
                topRatedTvShowsDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                topRatedTvShowsFragment.setData(topRatedTvShows);
            }

            topRatedTvShowsUpdateDownloader = new UpdateDownloader(topRatedTvShowsFragment.tvShowsListRecyclerView);
            topRatedTvShowsUpdateDownloader.setOnUpdateDownloaderListener(this);

        }

        if (onTheAirTvShowsFragment == null && i == 2) {
            String tag = "android:switcher:" + R.id.pager_tv_shows_activity + ":" + 2;
            onTheAirTvShowsFragment = (TvShowsFragment) getSupportFragmentManager().findFragmentByTag(tag);

            if (onTheAirTvShows != null) {
                onTheAirTvShowsDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                onTheAirTvShowsFragment.setData(onTheAirTvShows);
            }

            onTheAirTvShowsUpdateDownloader = new UpdateDownloader(onTheAirTvShowsFragment.tvShowsListRecyclerView);
            onTheAirTvShowsUpdateDownloader.setOnUpdateDownloaderListener(this);

        }

        if (airingTodayTvShowsFragment == null && i == 3) {
            String tag = "android:switcher:" + R.id.pager_tv_shows_activity + ":" + 3;
            airingTodayTvShowsFragment = (TvShowsFragment) getSupportFragmentManager().findFragmentByTag(tag);

            if (airingTodayTvShows != null) {
                airingTodayTvShowsDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                airingTodayTvShowsFragment.setData(airingTodayTvShows);
            }

            airingTodayTvShowsUpdateDownloader = new UpdateDownloader(airingTodayTvShowsFragment.tvShowsListRecyclerView);
            airingTodayTvShowsUpdateDownloader.setOnUpdateDownloaderListener(this);

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
