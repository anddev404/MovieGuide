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
