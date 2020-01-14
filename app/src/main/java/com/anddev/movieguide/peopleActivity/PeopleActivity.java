package com.anddev.movieguide.peopleActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.actorActivity.ActorActivity_;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.moviesActivity.MoviesFragment;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.InternetTools;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.RecyclerItemClickListener;
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

@EActivity(R.layout.activity_people)
public class PeopleActivity extends AppCompatActivity {

    Activity activity;
    PeopleFragment peopleFragment;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

    PopularPeople popularPeople;

    AlertDialog internetDialog;

    ConnectionInterface client;

    @AfterViews
    public void onCreate() {

        activity = this;
        ButterKnife.bind(this);

        navigationDrawer = new NavigationDrawerTools(activity, R.id.people_navigation_draver);
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle("People");
        peopleFragment = (PeopleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_people);
        StatusBarAndSoftKey.changeColor(this);

        try {

            client = RetrofitTools.getConnectionInterface();

            IntentFilter regFilter = new IntentFilter();
            regFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(networkChangeReceiver, regFilter);


        } catch (Exception e) {

        }

    }

    public void downloadAndShowPeopleOnScreen() {

        try {
            if (popularPeople == null) {
                if (InternetTools.isNetworkAvailable(activity)) {

                    downloadPeopleInBackground(client, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE, 1);

                } else {
                    internetDialog = InternetTools.showNoConnectionDialog(activity);
                }
            } else {
                // if (peopleListRecyclerView == null) {

                peopleFragment.setData(popularPeople);

                //}
            }

        } catch (Exception e) {

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        downloadAndShowPeopleOnScreen();

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
    void downloadPeopleInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {
        try {


            Call<PopularPeople> call = client.popularPeople(apiKey, language, page);

            call.enqueue(new Callback<PopularPeople>() {

                @Override
                public void onResponse(Call<PopularPeople> call, Response<PopularPeople> response) {

                    if (response.code() == 200) {

                        popularPeople = response.body();
                        peopleFragment.setData(popularPeople);
                    } else {

                        showError("Nie można pobrać odpowiednich danych");

                    }

                }

                @Override
                public void onFailure(Call<PopularPeople> call, Throwable t) {

                    showError("Brak połączenia internetowego!");
                    downloadAndShowPeopleOnScreen();

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");
            downloadAndShowPeopleOnScreen();

        }

    }


    @UiThread
    public void showError(String message) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getExtras() != null) {
                NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {

                    if (internetDialog != null) {

                        internetDialog.dismiss();

                    }
                    downloadAndShowPeopleOnScreen();

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
