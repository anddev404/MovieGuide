package com.anddev.movieguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anddev.movieguide.actorActivity.ActorActivity_;
import com.anddev.movieguide.model.Actor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ActorActivity_.class);
        startActivity(intent);


    }

}
