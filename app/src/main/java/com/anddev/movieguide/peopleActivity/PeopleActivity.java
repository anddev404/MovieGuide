package com.anddev.movieguide.peopleActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.BackStackManager;
import com.anddev.movieguide.tools.ConnectionInterface;
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

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_people)
public class PeopleActivity extends AppCompatActivity implements NetworkChangeReceiver.onSubmitListener, UpdateDownloader.OnUpdatePageDownloaderListener, ActionBarTools.OnChangeViewListener {

    Activity activity;
    PeopleFragment peopleFragment;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;
    NetworkChangeReceiver networkChangeReceiver;

    PopularPeople popularPeople;

    AlertDialog internetDialog;

    ConnectionInterface client;
    UpdateDownloader updateDownloader;

    @AfterViews
    public void onCreate() {

        activity = this;
        BackStackManager.getInstance().addActivity(this);
        ButterKnife.bind(this);

        navigationDrawer = new NavigationDrawerTools(activity, R.id.people_navigation_draver).setOtherColorForPeopleButton();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(MyApplication.getStringFromResource(R.string.people));
        peopleFragment = (PeopleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_people);
        updateDownloader = new UpdateDownloader(peopleFragment.peopleListRecyclerView);
        updateDownloader.setOnUpdateDownloaderListener(this);
        StatusBarAndSoftKey.changeColor(this);
        networkChangeReceiver = new NetworkChangeReceiver(this).setOnNetworkChangeReceiver(this);
        try {

            client = RetrofitTools.getConnectionInterface();

        } catch (Exception e) {

        }
        downloadAndShowPeopleOnScreen();
    }

    public void downloadAndShowPeopleOnScreen() {

        try {
            if (popularPeople == null) {
                if (InternetTools.isNetworkAvailable(activity)) {

                    downloadPeopleInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), 1);

                } else {
                    internetDialog = InternetTools.showNoConnectionDialog(activity);
                }
            }

        } catch (Exception e) {

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        networkChangeReceiver.registerNetworkChangeReceiver();

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeView(-1);
    }

    @Background
    void downloadPeopleInBackground(ConnectionInterface client, String apiKey, String language, Integer page) {
        try {


            Call<PopularPeople> call = client.popularPeople(apiKey, language, page);

            call.enqueue(new Callback<PopularPeople>() {

                @Override
                public void onResponse(Call<PopularPeople> call, Response<PopularPeople> response) {

                    if (response.code() == 200) {


                        if (page > 1) {
                            try {
                                if (response.body().getResults().size() > 0) {
                                    peopleFragment.addData(response.body());
                                    if (updateDownloader != null) {
                                        updateDownloader.downloadedPage(page);
                                    }
                                }
                            } catch (Exception e) {
                            }

                        } else {
                            popularPeople = response.body();
                            peopleFragment.setData(popularPeople);
                        }
                    } else {

                        if (updateDownloader != null) {
                            updateDownloader.notDownloadedPage(page);
                        }
                    }
                }

                @Override
                public void onFailure(Call<PopularPeople> call, Throwable t) {

                    if (updateDownloader != null) {
                        updateDownloader.notDownloadedPage(page);
                    }
                    downloadAndShowPeopleOnScreen();

                }
            });
        } catch (Throwable e) {
            if (updateDownloader != null) {
                updateDownloader.notDownloadedPage(page);
            }
            downloadAndShowPeopleOnScreen();

        }

    }

    @Override
    public void downloadPage(UpdateDownloader updateDownloader, int page) {

        downloadPeopleInBackground(client, RetrofitTools.API_KEY, LanguageTools.getLanguage(this), page);

    }

    @Override
    public void changeView(int viewType) {
        try {
            peopleFragment.setViewType(viewType);
            peopleFragment.initializeRecyclerViewAndSetAdapter();

        } catch (Exception e) {
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
        if (internetDialog != null) {

            internetDialog.dismiss();

        }
        downloadAndShowPeopleOnScreen();
    }

    @Override
    public void userTurnedInternetOff() {
    }

    //endregion
}
