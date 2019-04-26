package com.anddev.movieguide;

import com.anddev.movieguide.model.People;

public class PeopleSingleton {

    private static People instance = null;

    private PeopleSingleton() {

    }

    public static People getInstance() {
        return instance;
    }

    public static People setInstance(People people) {
        instance = people;
        return people;
    }

}
