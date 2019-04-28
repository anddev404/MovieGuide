package com.anddev.movieguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.anddev.movieguide.model.Actor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActorActivity extends AppCompatActivity {

    @BindView(R.id.nameTextView)
    TextView name;

    @BindView(R.id.birthdayTextView)
    TextView birthday;

    @BindView(R.id.place_of_birth_textView)
    TextView placeOfBirth;

    Actor actor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        ButterKnife.bind(this);

        actor = ActorSingleton.getInstance();
        try {
            showDataOfActor(actor);

        } catch (Exception e) {

        }

    }

    public void showDataOfActor(Actor actor) {

        name.setText(actor.getName());
        birthday.setText(actor.getBirthday());
        placeOfBirth.setText(actor.getPlace_of_birth());
    }
}
