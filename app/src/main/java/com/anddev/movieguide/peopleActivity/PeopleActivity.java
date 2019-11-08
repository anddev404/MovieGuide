package com.anddev.movieguide.peopleActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.actorActivity.ActorActivity;
import com.anddev.movieguide.actorActivity.ActorActivity_;
import com.anddev.movieguide.actorActivity.ProfilesAdapter;
import com.anddev.movieguide.model.Actor;
import com.anddev.movieguide.model.Images;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.DateTools;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.InternetTools;
import com.anddev.movieguide.tools.NavigationBarTools;
import com.anddev.movieguide.tools.RecyclerItemClickListener;
import com.anddev.movieguide.tools.RetrofitTools;

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
    NavigationBarTools navigationBarTools;

    @BindView(R.id.people_list_recycler_view)
    RecyclerView peopleListRecyclerView;

    PopularPeople popularPeople;

    AlertDialog internetDialog;

    ConnectionInterface client;

    @AfterViews
    public void onCreate() {

        activity = this;
        ButterKnife.bind(this);
        navigationBarTools = new NavigationBarTools(this);

        peopleListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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
                if (peopleListRecyclerView == null) {

                    showData(popularPeople);

                }
            }

        } catch (Exception e) {

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        downloadAndShowPeopleOnScreen();

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
                        showData(popularPeople);
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
    public void showData(final PopularPeople popularPeople) {

        PeopleAdapter adapter = new PeopleAdapter(this, popularPeople.getResults());
        peopleListRecyclerView.setAdapter(adapter);

        peopleListRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, peopleListRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(activity, ActorActivity_.class);
                        intent.putExtra("Id", popularPeople.getResults().get(position).getId());
                        startActivity(intent);
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

}
