package com.anddev.movieguide.searchEngineActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.moviesActivity.MoviesFragment;
import com.anddev.movieguide.peopleActivity.PeopleFragment;
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
import com.anddev.movieguide.tvShows.TvShowsFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_search_engine)
public class SearchEngineActivity extends AppCompatActivity implements DownloadManager.OnDownloadManagerListener, ActionBar.TabListener, NetworkChangeReceiver.onSubmitListener, UpdateDownloader.OnUpdatePageDownloaderListener, ActionBarTools.OnChangeViewListener {

    SearchEngineActivity activity;
    MoviesFragment moviesFragment;
    TvShowsFragment tvShowsFragment;
    PeopleFragment peopleFragment;
    DownloadManager moviesDownloadManager;
    DownloadManager tvShowsDownloadManager;
    DownloadManager peopleDownloadManager;
    UpdateDownloader moviesUpdateDownloader;
    UpdateDownloader tvShowsUpdateDownloader;
    UpdateDownloader peopleUpdateDownloader;

    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;
    NetworkChangeReceiver networkChangeReceiver;
    AlertDialog internetDialog;

    String query = "";
    ConnectionInterface client;
    Movies movies;
    TvShows tvShows;
    PopularPeople people;

    ViewPager viewPager;
    TabsPagerAdapter mAdapter;


    @AfterViews
    public void onCreate() {
        activity = this;
        BackStackManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.search_engine_navigation_draver).setNormalColorForAllButtons();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(MyApplication.getStringFromResource(R.string.search_results)).addTabsToView(TabsPagerAdapter.getTabs(), this);
        StatusBarAndSoftKey.changeColor(this);
        networkChangeReceiver = new NetworkChangeReceiver(this).setOnNetworkChangeReceiver(this);

        viewPager = (ViewPager) findViewById(R.id.pager_search_activity);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        client = RetrofitTools.getConnectionInterface();


        try {
            if (activity.getIntent().getExtras() != null) {
                query = activity.getIntent().getExtras().getString("Query", "");

            } else {

            }
        } catch (Exception e) {
        }

        moviesDownloadManager = new DownloadManager();
        tvShowsDownloadManager = new DownloadManager();
        peopleDownloadManager = new DownloadManager();

        moviesDownloadManager.setOnDownloadManagerListener(this);
        moviesDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        tvShowsDownloadManager.setOnDownloadManagerListener(this);
        tvShowsDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        peopleDownloadManager.setOnDownloadManagerListener(this);
        peopleDownloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

                if (moviesFragment == null && arg0 == 0) {
                    String tag = "android:switcher:" + R.id.pager_search_activity + ":" + 0;
                    moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    if (movies != null) {
                        moviesDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                        moviesFragment.setData(movies);
                    }

                    moviesUpdateDownloader = new UpdateDownloader(moviesFragment.moviesListRecyclerView);
                    moviesUpdateDownloader.setOnUpdateDownloaderListener(activity);

                }
                if (tvShowsFragment == null && arg0 == 1) {
                    String tag = "android:switcher:" + R.id.pager_search_activity + ":" + 1;
                    tvShowsFragment = (TvShowsFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    if (tvShows != null) {
                        tvShowsDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                        tvShowsFragment.setData(tvShows);
                    }

                    tvShowsUpdateDownloader = new UpdateDownloader(tvShowsFragment.tvShowsListRecyclerView);
                    tvShowsUpdateDownloader.setOnUpdateDownloaderListener(activity);

                }

                if (peopleFragment == null && arg0 == 2) {
                    String tag = "android:switcher:" + R.id.pager_search_activity + ":" + 2;
                    peopleFragment = (PeopleFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    if (people != null) {
                        peopleDownloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                        peopleFragment.setData(people);
                    }

                    peopleUpdateDownloader = new UpdateDownloader(peopleFragment.peopleListRecyclerView);
                    peopleUpdateDownloader.setOnUpdateDownloaderListener(activity);

                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

        });


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
    void downloadMoviesInBackground(ConnectionInterface client, String apiKey, String language, String query, Integer page) {
        try {


            Call<Movies> call = client.searchMovies(apiKey, language, query, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {

                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    if (moviesUpdateDownloader != null) {
                                        moviesUpdateDownloader.downloadedPage(page);
                                    }
                                }
                                moviesFragment.addData(response.body());
                            } catch (Exception e) {
                            }
                        } else {
                            movies = response.body();
                            moviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                        try {
                            actionBarTools.setTextToTabOfActionBar(0, getResources().getString(R.string.movies) + " (" + movies.getResults().size() + ")");

                        } catch (Exception e) {
                        }

                    } else {

                        moviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                        if (moviesUpdateDownloader != null) {
                            moviesUpdateDownloader.notDownloadedPage(page);
                        }
                    }
                    moviesDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                    moviesDownloadManager.changeStateDownloadInProgress(false);
                    moviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    if (moviesUpdateDownloader != null) {
                        moviesUpdateDownloader.notDownloadedPage(page);
                    }

                }
            });
        } catch (Throwable e) {
            moviesDownloadManager.changeStateDownloadInProgress(false);
            moviesDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            if (moviesUpdateDownloader != null) {
                moviesUpdateDownloader.notDownloadedPage(page);
            }
        }

    }

    @Background
    void downloadTvShowsInBackground(ConnectionInterface client, String apiKey, String language, String query, Integer page) {
        try {


            Call<TvShows> call = client.searchTvShows(apiKey, language, query, page);

            call.enqueue(new Callback<TvShows>() {

                @Override
                public void onResponse(Call<TvShows> call, Response<TvShows> response) {

                    if (response.code() == 200) {

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    if (tvShowsUpdateDownloader != null) {
                                        tvShowsUpdateDownloader.downloadedPage(page);
                                    }
                                }
                                tvShowsFragment.addData(response.body());
                            } catch (Exception e) {
                            }
                        } else {
                            tvShows = response.body();
                            tvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                        try {
                            actionBarTools.setTextToTabOfActionBar(1, getResources().getString(R.string.tv_shows) + " (" + tvShows.getResults().size() + ")");

                        } catch (Exception e) {
                        }

                    } else {

                        tvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                        if (tvShowsUpdateDownloader != null) {
                            tvShowsUpdateDownloader.notDownloadedPage(page);
                        }
                    }
                    tvShowsDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<TvShows> call, Throwable t) {

                    tvShowsDownloadManager.changeStateDownloadInProgress(false);
                    tvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    if (tvShowsUpdateDownloader != null) {
                        tvShowsUpdateDownloader.notDownloadedPage(page);
                    }

                }
            });
        } catch (Throwable e) {
            tvShowsDownloadManager.changeStateDownloadInProgress(false);
            tvShowsDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
            if (tvShowsUpdateDownloader != null) {
                tvShowsUpdateDownloader.notDownloadedPage(page);
            }
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

                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    if (peopleUpdateDownloader != null) {
                                        peopleUpdateDownloader.downloadedPage(page);
                                    }
                                    peopleFragment.addData(response.body());
                                }

                            } catch (Exception e) {
                            }

                        } else {
                            people = response.body();
                            peopleDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);
                        }

                        try {
                            actionBarTools.setTextToTabOfActionBar(2, getResources().getString(R.string.people) + " (" + people.getResults().size() + ")");

                        } catch (Exception e) {

                        }

                    } else {

                        peopleDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                        if (peopleUpdateDownloader != null) {
                            peopleUpdateDownloader.notDownloadedPage(page);
                        }
                    }
                    peopleDownloadManager.changeStateDownloadInProgress(false);

                }

                @Override
                public void onFailure(Call<PopularPeople> call, Throwable t) {
                    try {
                        if (peopleDownloadManager != null) {
                            peopleDownloadManager.changeStateDownloadInProgress(false);
                            peopleDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                        }
                        if (peopleUpdateDownloader != null) {
                            peopleUpdateDownloader.notDownloadedPage(page);
                        }
                    } catch (Throwable e) {
                    }
                }
            });
        } catch (Throwable e) {
            try {
                if (peopleDownloadManager != null) {
                    peopleDownloadManager.changeStateDownloadInProgress(false);
                    peopleDownloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);
                    if (peopleUpdateDownloader != null) {
                        peopleUpdateDownloader.notDownloadedPage(page);
                    }
                }
            } catch (Throwable t) {
            }

        }

    }

    @Override
    public void downloadPage(UpdateDownloader updateDownloader, int page) {
        try {

            if (updateDownloader == moviesUpdateDownloader) {
                downloadMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), query, page);

            }
            if (updateDownloader == tvShowsUpdateDownloader) {
                downloadTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), query, page);

            }
            if (updateDownloader == peopleUpdateDownloader) {
                downloadPeopleInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), query, page);

            }
        } catch (Exception e) {
        }
    }

    public static void searchAndOpenResults(String query, Activity activity) {
        Intent intent = new Intent(activity, SearchEngineActivity_.class);
        intent.putExtra("Query", query);
        activity.startActivity(intent);
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
    public void changeView(int viewType) {
        try {

            Fragment actualFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager_search_activity + ":" + viewPager.getCurrentItem());

            if (actualFragment == moviesFragment) {
                moviesFragment.setViewType(viewType);
                moviesFragment.initializeRecyclerViewAndSetAdapter();
            }
            if (actualFragment == tvShowsFragment) {
                tvShowsFragment.setViewType(viewType);
                tvShowsFragment.initializeRecyclerViewAndSetAdapter();
            }
            if (actualFragment == peopleFragment) {
                peopleFragment.setViewType(viewType);
                peopleFragment.initializeRecyclerViewAndSetAdapter();
            }


        } catch (Exception e) {
        }
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

    @Override
    public void showData(DownloadManager downloadManager) {

        if (downloadManager == moviesDownloadManager) {
            if (moviesFragment != null) {
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                moviesFragment.setData(movies);
            }

        }
        if (downloadManager == tvShowsDownloadManager) {
            if (tvShowsFragment != null) {
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                tvShowsFragment.setData(tvShows);
            }
        }
        if (downloadManager == peopleDownloadManager) {
            if (peopleFragment != null) {
                downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
                peopleFragment.setData(people);
            }
        }
    }

    @Override
    public void downloadData(DownloadManager downloadManager) {

        if (downloadManager == moviesDownloadManager) {
            moviesDownloadManager.changeStateDownloadInProgress(true);
            downloadMoviesInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), query, 1);
        }
        if (downloadManager == tvShowsDownloadManager) {
            tvShowsDownloadManager.changeStateDownloadInProgress(true);
            downloadTvShowsInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), query, 1);
        }
        if (downloadManager == peopleDownloadManager) {
            peopleDownloadManager.changeStateDownloadInProgress(true);
            downloadPeopleInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), query, 1);
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
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    //region checkInternetConnection

    @Override
    public void userTurnedInternetOn() {
        moviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        tvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
        peopleDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);
    }

    @Override
    public void userTurnedInternetOff() {
        moviesDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        tvShowsDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
        peopleDownloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);
    }

    //endregion
}
