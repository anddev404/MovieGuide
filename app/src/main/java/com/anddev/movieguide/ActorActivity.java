package com.anddev.movieguide;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anddev.movieguide.model.Actor;
import com.anddev.movieguide.model.RecyclerItemClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EActivity(R.layout.activity_actor)
public class ActorActivity extends AppCompatActivity {

    Activity activity;

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

    Actor actor;
    KnownFor knownFor;

    @AfterViews
    public void onCreate() {
        activity = this;
        ButterKnife.bind(this);

        knownForRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//dla widoku horyzontalnego

        try {

            ConnectionInterface client = RetrofitTools.getConnectionInterface();
            downloadActorInBackground(client, 976, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE);
            downloadKnownForInBackground(client, 976, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE);

        } catch (Exception e) {

        }

    }


    @Background
    void downloadActorInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {


            Call<Actor> call = client.dataOfPerson(id, apiKey, language);

            call.enqueue(new Callback<Actor>() {

                @Override
                public void onResponse(Call<Actor> call, Response<Actor> response) {

                    if (response.code() == 200) {

                        actor = response.body();
                        showDataOfActor(actor);
                        ImageTools.getImageFromInternet(activity, "https://image.tmdb.org/t/p/w500/" + response.body().getProfile_path(), imageViewActor, ImageTools.DRAWABLE_PERSON);

                    } else {

                        showError("Nie można pobrać odpowiednich danych");
                        ImageTools.getImageFromInternet(activity, "brak adresu", imageViewActor, ImageTools.DRAWABLE_PERSON);

                    }

                }

                @Override
                public void onFailure(Call<Actor> call, Throwable t) {

                    showError("Brak połączenia internetowego!");

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwan błąd!");
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

                        // ImageTools.getImageFromInternet(activity, "https://image.tmdb.org/t/p/w500/" + response.body().getProfile_path(), imageViewActor);

                        knownFor = response.body();
                        showKnownFor(knownFor);

                    } else {

                        showError("Nie można pobrać odpowiednich danych");
                        ImageTools.getImageFromInternet(activity, "brak adresu", imageViewActor, ImageTools.DRAWABLE_PERSON);

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

    @UiThread
    public void showDataOfActor(Actor actor) {

        name.setText(actor.getName());
        birthday.setText(actor.getBirthday());
        placeOfBirth.setText(actor.getPlace_of_birth());
        age.setText(DateTools.calculateAgeFromBirthday(actor.getBirthday()));

    }

    @UiThread
    public void showKnownFor(final KnownFor knownFor) {

        KnownForAdapter adapter = new KnownForAdapter(this, knownFor.getCast());
        knownForRecyclerView.setAdapter(adapter);

        knownForRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, knownForRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(activity, "Kliknięto " + knownFor.getCast().get(position).getOriginal_title(), Toast.LENGTH_SHORT).show();
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
}
