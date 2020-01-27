package com.anddev.movieguide.actorActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Actor;
import com.anddev.movieguide.model.Images;
import com.anddev.movieguide.model.KnownFor;
import com.anddev.movieguide.model.Profiles;
import com.anddev.movieguide.movieActivity.MovieActvity_;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.DateTools;
import com.anddev.movieguide.tools.DownloadManager;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.InternetTools;
import com.anddev.movieguide.tools.LanguageTools;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.RecyclerItemClickListener;
import com.anddev.movieguide.tools.RetrofitTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_actor)
public class ActorActivity extends AppCompatActivity implements DownloadManager.OnDownloadManagerListener {

    //region variables
    Activity activity;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

    @BindView(R.id.nameTextView)
    TextView name;

    @BindView(R.id.birthdayTextView)
    TextView birthday;

    @BindView(R.id.place_of_birth_textView)
    TextView placeOfBirth;

    @BindView(R.id.age)
    TextView age;

    @BindView(R.id.imageViewActor)
    ImageView imageViewActor;

    @BindView(R.id.known_for_recycler_view)
    RecyclerView knownForRecyclerView;

    @BindView(R.id.images_recycler_view)
    RecyclerView imagesRecyclerView;

    @BindView(R.id.full_screen_images_recycler_view)
    RecyclerView fullScreenImagesRecyclerView;

    DownloadManager downloadManager;
    ConnectionInterface client;
    AlertDialog internetDialog;

    Actor actor;
    KnownFor knownFor;
    Images images;
    Integer actorId;

    SnapHelper snapHelper;
    LinearLayoutManager layoutManager;

    //endregion

    //region activity
    @AfterViews
    public void onCreate() {
        activity = this;
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.actor_navigation_draver).setNormalColorForAllButtons();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(getString(R.string.Actor));
        StatusBarAndSoftKey.changeColor(this);

        try {
            if (activity.getIntent().getExtras() != null) {
                actorId = activity.getIntent().getExtras().getInt("Id", 0);
            } else {
                actorId = 0;
            }
        } catch (Exception e) {
            actorId = 0;
        }
        knownForRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//dla widoku horyzontalnego
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//dla widoku horyzontalnego

        fullScreenImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//dla widoku horyzontalnego
        fullScreenImagesRecyclerView.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        snapHelper = new PagerSnapHelper();
        fullScreenImagesRecyclerView.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(fullScreenImagesRecyclerView);

        fullScreenImagesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(layoutManager);
                    int pos = layoutManager.getPosition(centerView);
                    actionBarTools.setTitle((pos + 1) + "/" + images.getProfiles().size() + "  " + actor.getName()).addBackButton();

                }
            }
        });

        try {

            client = RetrofitTools.getConnectionInterface();

        } catch (Exception e) {

        }

        downloadManager = new DownloadManager(InternetTools.isNetworkAvailable(activity), false);
        downloadManager.setOnDownloadManagerListener(this);
        downloadManager.processStates();

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkChangeReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkChangeReceiver();
        actionBarTools.closeSearchEngineIfOpen();

        if (navigationDrawer != null) {
            if (navigationDrawer.closeNavigationDrawerIfOpen()) {
            }
        }
    }

    //endregion

    //region download
    @Background
    void downloadActorInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {


            Call<Actor> call = client.dataOfPerson(id, apiKey, language);

            call.enqueue(new Callback<Actor>() {

                @Override
                public void onResponse(Call<Actor> call, Response<Actor> response) {

                    if (response.code() == 200) {

                        actor = response.body();
                        ImageTools.getImageFromInternet(activity, "https://image.tmdb.org/t/p/w500/" + response.body().getProfile_path(), imageViewActor, ImageTools.DRAWABLE_PERSON);
                        downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD);

                    } else {

                        showError("Nie można pobrać odpowiednich danych");
                        downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                    }

                }

                @Override
                public void onFailure(Call<Actor> call, Throwable t) {

                    showError("Brak połączenia internetowego!");
                    downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD);

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");
        }

    }

    @Background
    void downloadKnownForInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {


            Call<KnownFor> call = client.knownFor(id, apiKey, language);

            call.enqueue(new Callback<KnownFor>() {

                @Override
                public void onResponse(Call<KnownFor> call, Response<KnownFor> response) {

                    if (response.code() == 200) {

                        knownFor = response.body();
                        showKnownFor(knownFor);

                    } else {

                        showError("Nie można pobrać odpowiednich danych");

                    }

                }

                @Override
                public void onFailure(Call<KnownFor> call, Throwable t) {

                    showError("Brak połączenia internetowego podczas pobierania filmów w których grał aktor!");

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwan błąd!");
        }

    }

    @Background
    void downloadImagesOfActorInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {


            Call<Images> call = client.images(id, apiKey, language);

            call.enqueue(new Callback<Images>() {

                @Override
                public void onResponse(Call<Images> call, Response<Images> response) {

                    if (response.code() == 200) {

                        images = response.body();
                        showImages(images);

                    } else {

                        showError("Nie można pobrać odpowiednich danych");

                    }

                }

                @Override
                public void onFailure(Call<Images> call, Throwable t) {

                    showError("Brak połączenia internetowego podczas pobierania filmów w których grał aktor!");

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwan błąd!");
        }

    }
//endregion

    //region show data
    @UiThread
    public void showDataOfActor(Actor actor) {

        name.setText(actor.getName());
        birthday.setText(actor.getBirthday());
        placeOfBirth.setText(actor.getPlace_of_birth());

        String yearsOld = DateTools.calculateAgeFromBirthday(actor.getBirthday());
        if (yearsOld != null && yearsOld.length() > 0) {
            age.setText(yearsOld + " " + getString(R.string.years_old));
        }


    }

    @UiThread
    public void showKnownFor(final KnownFor knownFor) {

        KnownForAdapter adapter = new KnownForAdapter(this, knownFor.getCast());
        knownForRecyclerView.setAdapter(adapter);

        knownForRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, knownForRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(activity, MovieActvity_.class);
                        intent.putExtra("Id", knownFor.getCast().get(position).getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                })
        );

    }

    @UiThread
    public void showImages(final Images images) {

        ProfilesAdapter adapter = new ProfilesAdapter(this, images.getProfiles());
        imagesRecyclerView.setAdapter(adapter);

        showImagesFullScreen(images.getProfiles());

        imagesRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, imagesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        actionBarTools.setTitle((position + 1) + "/" + images.getProfiles().size() + "  " + actor.getName()).addBackButton();

                        fullScreenImagesRecyclerView.setVisibility(View.VISIBLE);
                        fullScreenImagesRecyclerView.scrollToPosition(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                })
        );

    }

    @UiThread
    public void showImagesFullScreen(final List<Profiles> profiles) {

        ImageAdapter adapter = new ImageAdapter(this, profiles);
        fullScreenImagesRecyclerView.setAdapter(adapter);

        fullScreenImagesRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, knownForRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        fullScreenImagesRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                })
        );

    }

    @UiThread
    public void showError(String message) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }
    //endregion

    //region actionBar
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

                if (fullScreenImagesRecyclerView != null && fullScreenImagesRecyclerView.getVisibility() == View.VISIBLE) {

                    actionBarTools.setTitle(getString(R.string.Actor)).addMenuButton();
                    fullScreenImagesRecyclerView.setVisibility(View.GONE);

                } else {

                    navigationDrawer.openOrCloseNavigationDrawer();

                }

                break;

        }

        return true;

    }

    //endregion

    //region back Button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


            if (navigationDrawer != null) {
                if (navigationDrawer.closeNavigationDrawerIfOpen()) {
                    return true;
                }

            }

            if (fullScreenImagesRecyclerView != null && fullScreenImagesRecyclerView.getVisibility() == View.VISIBLE) {
                actionBarTools.setTitle(getString(R.string.Actor)).addMenuButton();
                fullScreenImagesRecyclerView.setVisibility(View.GONE);
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }
    //endregion

    //region checkInternetConnection
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

    public void registerNetworkChangeReceiver() {
        IntentFilter regFilter = new IntentFilter();
        regFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, regFilter);
    }

    public void unregisterNetworkChangeReceiver() {
        unregisterReceiver(networkChangeReceiver);
    }

    //endregion

    @Override
    public void downloadData() {

        downloadActorInBackground(client, actorId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));
        downloadKnownForInBackground(client, actorId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));
        downloadImagesOfActorInBackground(client, actorId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));
    }

    @Override
    public void showData() {
        showDataOfActor(actor);
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


}
