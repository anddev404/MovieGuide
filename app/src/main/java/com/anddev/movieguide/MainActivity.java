package com.anddev.movieguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anddev.movieguide.model.People;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        People p = People.getExamplePeople();
    }

}
