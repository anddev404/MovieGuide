package com.anddev.movieguide.moviesActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity_;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.DownloadManager;
import com.anddev.movieguide.tools.InternetTools;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.RetrofitTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_movies)
public class MoviesActivity extends AppCompatActivity implements DownloadManager.OnDownloadManagerListener {

    Activity activity;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

    MoviesFragment fragment;
    ConnectionInterface client;
    DownloadManager downloadManager;
    AlertDialog internetDialog;
    Movies movies;

    @AfterViews
    public void onCreate() {
        activity = this;
        navigationDrawer = new NavigationDrawerTools(activity, R.id.movies_navigation_draver);
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle("Movies");
        StatusBarAndSoftKey.changeColor(this);

        fragment = (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movies);
        client = RetrofitTools.getConnectionInterface();

        IntentFilter regFilter = new IntentFilter();
        regFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, regFilter);

        downloadManager = new DownloadManager(InternetTools.isNetworkAvailable(activity), false);
        downloadManager.setOnDownloadManagerListener(this);
        downloadManager.processStates();

    }

    @Background
    void downloadMoviesInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {
        try {


            Call<Movies> call = client.popularMovie(apiKey, language, page);

            call.enqueue(new Callback<Movies>() {

                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {

                    if (response.code() == 200) {

                        movies = response.body();
                        downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);

                    } else {

                        showError("Nie można pobrać odpowiednich danych");
                        downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }

                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                    showError("Brak połączenia internetowego!");
                    downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");
            downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

        }

    }

    @UiThread
    public void showError(String message) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void downloadData() {
        try {

            downloadMoviesInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, 1);

        } catch (Exception e) {

        }
    }

    @Override
    public void showData() {
        fragment.setData(movies);

    }

    @Override
    public void showNoInternetNotification() {
        if (internetDialog != null) {

            internetDialog.show();

        } else {
            internetDialog = InternetTools.showNoConnectionDialog(activity);

        }

    }

    @Override
    public void hideNoInternetNotification() {
        if (internetDialog != null) {

            internetDialog.dismiss();

        }
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getExtras() != null) {
                NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {

                    downloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_INTERNET_CONNECTION);

                } else {
                    downloadManager.changeStateInternetConnection(DownloadManager.THERE_IS_NO_INTERNET_CONNECTION);

                }
            }

        }
    };

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

}
