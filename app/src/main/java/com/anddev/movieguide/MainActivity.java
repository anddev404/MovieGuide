package com.anddev.movieguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anddev.movieguide.model.People;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeopleSingleton.setInstance(People.getExamplePeople());
        Intent intent = new Intent(this, PeopleActivity.class);
        startActivity(intent);


    }

}
