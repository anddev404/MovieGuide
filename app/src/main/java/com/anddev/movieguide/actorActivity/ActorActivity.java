package com.anddev.movieguide.actorActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Actor;
import com.anddev.movieguide.model.Favourite;
import com.anddev.movieguide.model.Images;
import com.anddev.movieguide.model.KnownFor;
import com.anddev.movieguide.model.Profiles;
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
import com.anddev.movieguide.tools.MyApplication;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.NetworkChangeReceiver;
import com.anddev.movieguide.tools.RecyclerItemClickListener;
import com.anddev.movieguide.tools.RetrofitTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
public class ActorActivity extends AppCompatActivity implements DownloadManager.OnDownloadManagerListener, NetworkChangeReceiver.onSubmitListener {

    //region variables
    Activity activity;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;
    NetworkChangeReceiver networkChangeReceiver;

    Integer color;

    @BindView(R.id.actor_linear_layout_1)
    LinearLayout linearLayout1;

    @BindView(R.id.actor_linear_layout_2)
    LinearLayout linearLayout2;

    @BindView(R.id.actor_linear_layout_3)
    LinearLayout linearLayout3;

    @BindView(R.id.actor_linear_layout_4)
    LinearLayout linearLayout4;

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

    @BindView(R.id.actor_favourite_FloatingActionButton)
    FloatingActionButton favouriteFloatingActionButton;

    DownloadManager downloadManager;
    ConnectionInterface client;
    AlertDialog internetDialog;
    FavouriteTools favouriteTools;

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
        BackStackManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.actor_navigation_draver).setNormalColorForAllButtons();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(MyApplication.getStringFromResource(R.string.actor));
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

        try {
            if (activity.getIntent().getExtras() != null) {
                color = activity.getIntent().getExtras().getInt("color", 0);
            }
        } catch (Exception e) {
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
                    if (actor != null) {
                        actionBarTools.setTitle((pos + 1) + "/" + images.getProfiles().size() + "  " + actor.getName()).addBackButton();
                    } else {
                        actionBarTools.setTitle((pos + 1) + "/" + images.getProfiles().size()).addBackButton();
                    }

                }
            }
        });

        try {

            client = RetrofitTools.getConnectionInterface();

        } catch (Exception e) {

        }
        networkChangeReceiver = new NetworkChangeReceiver(this).setOnNetworkChangeReceiver(this);
        downloadManager = new DownloadManager();
        downloadManager.setOnDownloadManagerListener(this);
        downloadManager.initializeByCheckingInternetState(InternetTools.isNetworkAvailable(activity));

        favouriteTools = new FavouriteTools(activity);

        changeViewColor();

        try {
            if (imageViewActor != null) {

                imageViewActor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imagesRecyclerView != null) {
                            try {
                                imagesOnFullScreen(0);
                            } catch (Exception e) {

                            }
                        }

                    }
                });
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
        networkChangeReceiver.unregisterNetworkChangeReceiver(activity);
        actionBarTools.closeSearchEngineIfOpen();

        if (navigationDrawer != null) {
            if (navigationDrawer.closeNavigationDrawerIfOpen()) {
            }
        }
    }

    //endregion

    void imagesOnFullScreen(int position) {
        try {
            if (actionBarTools != null) {
                if (images.getProfiles() != null) {
                    if (actor != null) {
                        actionBarTools.setTitle((position + 1) + "/" + images.getProfiles().size() + "  " + actor.getName()).addBackButton();
                    } else {
                        actionBarTools.setTitle((position + 1) + "/" + images.getProfiles().size()).addBackButton();
                    }
                } else {
                    actionBarTools.setTitle(actor.getName()).addBackButton();

                }
            }
            if (fullScreenImagesRecyclerView != null) {
                favouriteFloatingActionButton.hide();
                fullScreenImagesRecyclerView.setVisibility(View.VISIBLE);
                fullScreenImagesRecyclerView.scrollToPosition(position);
            }
        } catch (Exception e) {

        }
    }

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
                linearLayout3.getBackground().setAlpha(180);

                linearLayout4.setBackgroundColor(color);
                linearLayout4.getBackground().setAlpha(60);

                favouriteFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));

                StatusBarAndSoftKey.changeColor(activity, color);

                ColorDrawable colorDrawable
                        = new ColorDrawable(color);
                getSupportActionBar().setBackgroundDrawable(colorDrawable);
            }
        } catch (Exception e) {
        }
    }

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
                        downloadManager.changeStateDownloadInProgress(false);

                    }
                }

                @Override
                public void onFailure(Call<Actor> call, Throwable t) {

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
    void downloadKnownForInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {


            Call<KnownFor> call = client.knownForCombined(id, apiKey, language);

            call.enqueue(new Callback<KnownFor>() {

                @Override
                public void onResponse(Call<KnownFor> call, Response<KnownFor> response) {

                    if (response.code() == 200) {

                        knownFor = response.body();
                        showKnownFor(knownFor);

                    }

                }

                @Override
                public void onFailure(Call<KnownFor> call, Throwable t) {
                }
            });
        } catch (Throwable e) {
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

                    }

                }

                @Override
                public void onFailure(Call<Images> call, Throwable t) {
                }
            });
        } catch (Throwable e) {
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

//        knownForRecyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(activity, knownForRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//
//                        String type = "";
//                        try {
//                            type = knownFor.getCast().get(position).getMedia_type();
//
//                            if (type.equalsIgnoreCase("movie")) {
//                                Intent intent = new Intent(activity, MovieActvity_.class);
//                                intent.putExtra("Id", knownFor.getCast().get(position).getId());
//                                try {
//                                    intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.moviesImageView, 0));
//
//                                } catch (Exception e) {
//                                }
//                                startActivity(intent);
//                            } else if (type.equalsIgnoreCase("tv")) {
//                                Intent intent = new Intent(activity, TvShowActivity_.class);
//                                intent.putExtra("Id", knownFor.getCast().get(position).getId());
//                                try {
//                                    intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.moviesImageView, 0));
//
//                                } catch (Exception e) {
//                                }
//                                startActivity(intent);
//                            }
//
//                        } catch (Exception e) {
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                    }
//
//                })
//        );

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
                        imagesOnFullScreen(position);
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
                    favouriteFloatingActionButton.show();

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
                favouriteFloatingActionButton.show();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
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

    @Override
    public void downloadData(DownloadManager downloadManager) {

        downloadManager.changeStateDownloadInProgress(true);

        downloadActorInBackground(client, actorId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));
        if (knownFor == null) {
            downloadKnownForInBackground(client, actorId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));
        }
        if (images == null) {
            downloadImagesOfActorInBackground(client, actorId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));
        }
    }

    @Override
    public void showData(DownloadManager downloadManager) {
        downloadManager.changeStateDataShowing(DownloadManager.DATA_IS_SHOWING);
        showDataOfActor(actor);

        try {
            favouriteTools.manageFavouriteFloatingActionButtom(favouriteFloatingActionButton, actorId, Favourite.FAVOURITE_ACTOR, actor.getName(), "", "", actor.getProfile_path());
        } catch (Exception e) {
            favouriteTools.manageFavouriteFloatingActionButtom(favouriteFloatingActionButton, actorId, Favourite.FAVOURITE_ACTOR, "id: " + actorId, "", "", "");
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


}
