package com.anddev.movieguide.searchEngineActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.moviesActivity.MoviesFragment;
import com.anddev.movieguide.peopleActivity.PeopleFragment;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.FragmentDownloadManager;
import com.anddev.movieguide.tools.InternetTools;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.RetrofitTools;
import com.anddev.movieguide.tvShows.TvShowsFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_search_engine)
public class SearchEngineActivity extends AppCompatActivity implements FragmentDownloadManager.OnFragmentDownloadManagerListener, ActionBar.TabListener {

    SearchEngineActivity activity;
    MoviesFragment moviesFragment;
    TvShowsFragment tvShowsFragment;
    PeopleFragment peopleFragment;
    FragmentDownloadManager fragmentDownloadManager;

    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

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
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.search_engine_navigation_draver);
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle("Search Results").addTabsToView(TabsPagerAdapter.getTabs(), this);

        viewPager = (ViewPager) findViewById(R.id.pager_search_activity);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
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

        fragmentDownloadManager = new FragmentDownloadManager();

        IntentFilter regFilter = new IntentFilter();
        regFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(networkChangeReceiver, regFilter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

                if (moviesFragment == null && arg0 == 0) {
                    String tag = "android:switcher:" + R.id.pager_search_activity + ":" + 0;
                    moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    fragmentDownloadManager.setOnDownloadManagerListener(activity, moviesFragment, InternetTools.isNetworkAvailable(activity), FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, FragmentDownloadManager.FRAGMENT_IS_NO_CREATED);
                    fragmentDownloadManager.changeStateFragmentIsCreated(FragmentDownloadManager.FRAGMENT_IS_CREATED, moviesFragment);
                }
                if (tvShowsFragment == null && arg0 == 1) {
                    String tag = "android:switcher:" + R.id.pager_search_activity + ":" + 1;
                    tvShowsFragment = (TvShowsFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    fragmentDownloadManager.setOnDownloadManagerListener(activity, tvShowsFragment, InternetTools.isNetworkAvailable(activity), FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, FragmentDownloadManager.FRAGMENT_IS_NO_CREATED);
                    fragmentDownloadManager.changeStateFragmentIsCreated(FragmentDownloadManager.FRAGMENT_IS_CREATED, tvShowsFragment);
                }
                if (peopleFragment == null && arg0 == 2) {
                    String tag = "android:switcher:" + R.id.pager_search_activity + ":" + 2;
                    peopleFragment = (PeopleFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    fragmentDownloadManager.setOnDownloadManagerListener(activity, peopleFragment, InternetTools.isNetworkAvailable(activity), FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, FragmentDownloadManager.FRAGMENT_IS_NO_CREATED);
                    fragmentDownloadManager.changeStateFragmentIsCreated(FragmentDownloadManager.FRAGMENT_IS_CREATED, peopleFragment);
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

        });


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
                        fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_DOWNLOAD, moviesFragment);


                    } else {

                        showError("Nie można pobrać odpowiednich danych");
                        fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, moviesFragment);

                    }

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                    showError("Brak połączenia internetowego!");
                    fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, moviesFragment);

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");
            fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, moviesFragment);

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

                        tvShows = response.body();
                        fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_DOWNLOAD, tvShowsFragment);


                    } else {

                        showError("Nie można pobrać odpowiednich danych");
                        fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, tvShowsFragment);

                    }

                }

                @Override
                public void onFailure(Call<TvShows> call, Throwable t) {

                    showError("Brak połączenia internetowego!");
                    fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, tvShowsFragment);

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");
            fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, tvShowsFragment);

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
                        fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_DOWNLOAD, peopleFragment);


                    } else {

                        showError("Nie można pobrać odpowiednich danych");
                        fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, peopleFragment);

                    }

                }

                @Override
                public void onFailure(Call<PopularPeople> call, Throwable t) {

                    showError("Brak połączenia internetowego!");
                    fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, peopleFragment);

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");
            fragmentDownloadManager.changeStateDataDownload(FragmentDownloadManager.DATA_IS_NOT_DOWNLOAD, peopleFragment);

        }

    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getExtras() != null) {
                NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {

                    fragmentDownloadManager.changeStateInternetConnection(FragmentDownloadManager.THERE_IS_INTERNET_CONNECTION, moviesFragment);
                    fragmentDownloadManager.changeStateInternetConnection(FragmentDownloadManager.THERE_IS_INTERNET_CONNECTION, tvShowsFragment);
                    fragmentDownloadManager.changeStateInternetConnection(FragmentDownloadManager.THERE_IS_INTERNET_CONNECTION, peopleFragment);


                } else {

                    fragmentDownloadManager.changeStateInternetConnection(fragmentDownloadManager.THERE_IS_NO_INTERNET_CONNECTION, moviesFragment);
                    fragmentDownloadManager.changeStateInternetConnection(fragmentDownloadManager.THERE_IS_NO_INTERNET_CONNECTION, tvShowsFragment);
                    fragmentDownloadManager.changeStateInternetConnection(fragmentDownloadManager.THERE_IS_NO_INTERNET_CONNECTION, peopleFragment);

                }
            }

        }
    };

    @UiThread
    public void showError(String message) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }

    public static void searchAndOpenResults(String query, Activity activity) {
        Intent intent = new Intent(activity, SearchEngineActivity_.class);
        intent.putExtra("Query", query);
        activity.startActivity(intent);
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

    @Override
    public void showData(Fragment fragment) {

        if (fragment == moviesFragment) {
            moviesFragment.setData(movies);
        }
        if (fragment == tvShowsFragment) {
            tvShowsFragment.setData(tvShows);
        }
        if (fragment == peopleFragment) {
            peopleFragment.setData(people);
        }
    }

    @Override
    public void downloadData(Fragment fragment) {

        if (fragment == moviesFragment) {
            downloadMoviesInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, query, 1);
        }
        if (fragment == tvShowsFragment) {
            downloadTvShowsInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, query, 1);
        }
        if (fragment == peopleFragment) {
            downloadPeopleInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, query, 1);
        }
    }

    @Override
    public void showNoInternetNotification(Fragment fragment) {

    }

    @Override
    public void hideNoInternetNotification(Fragment fragment) {

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
}
