package com.anddev.movieguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.anddev.movieguide.model.People;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleActivity extends AppCompatActivity {

    @BindView(R.id.nameTextView)
    TextView name;

    @BindView(R.id.birthdayTextView)
    TextView birthday;

    @BindView(R.id.place_of_birth_textView)
    TextView placeOfBirth;

    People people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        ButterKnife.bind(this);

        people = PeopleSingleton.getInstance();
        try {
            showDataOfActor(people);

        } catch (Exception e) {

        }

    }

    public void showDataOfActor(People people) {

        name.setText(people.getName());
        birthday.setText(people.getBirthday());
        placeOfBirth.setText(people.getPlace_of_birth());
    }
}
