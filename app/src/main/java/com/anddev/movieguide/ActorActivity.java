package com.anddev.movieguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.anddev.movieguide.model.Actor;

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

    @BindView(R.id.nameTextView)
    TextView name;

    @BindView(R.id.birthdayTextView)
    TextView birthday;

    @BindView(R.id.place_of_birth_textView)
    TextView placeOfBirth;

    Actor actor;

    @AfterViews
    public void onCreate() {

        ButterKnife.bind(this);

        actor = ActorSingleton.getInstance();
        try {
            showDataOfActor(actor);

        } catch (Exception e) {

        }
        background();
    }


    @Background
    void background() {
         try {
             String API_BASE_URL = "https://api.themoviedb.org";

             OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
             //
             Retrofit.Builder builder =
                     new Retrofit.Builder()
                             .baseUrl(API_BASE_URL)
                             .addConverterFactory(
                                     GsonConverterFactory.create()
                             );
             //
             Retrofit retrofit =
                     builder
                             .client(
                                     httpClient.build()
                             )
                             .build();
             //
             ConnectionInterface client = retrofit.create(ConnectionInterface.class);
             //
//// Fetch a list of the Github repositories.
             Call<Actor> call =
//                     client.reposForUser(976, "3a3657f217097dc333bd92af0d39bee4", "pl-PL");
                     client.reposForUser(976,"3a3657f217097dc333bd92af0d39bee4","pl-PL");
            String sss= call.request().toString();
// Execute the call asynchronously. Get a positive or negative callback.
             call.enqueue(new Callback<Actor>() {

                 @Override
                 public void onResponse(Call<Actor> call, Response<Actor> response) {
                     // The network call was a success and we got a response
                     // TODO: use the repository list and display it
                     Toast.makeText(ActorActivity.this, "Good " + response.message() + "\n\n\n" + response.code(), Toast.LENGTH_LONG).show();
                     // text.setText(response.body().toString());
                     name.setText(response.body().toString());
                 }

                 @Override
                 public void onFailure(Call<Actor> call, Throwable t) {
                     // the network call was a failure
                     Toast.makeText(ActorActivity.this, "Bad " + t.getMessage(), Toast.LENGTH_LONG).show();
                     //text.setText(t.toString() + "\n\n" + t.getMessage());
                 }
             });
                 } catch (Exception e) {

                 }

    }

    @UiThread
    void updateUI(Integer result) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void showDataOfActor(Actor actor) {

        name.setText(actor.getName());
        birthday.setText(actor.getBirthday());
        placeOfBirth.setText(actor.getPlace_of_birth());
    }


}
