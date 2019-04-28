package com.anddev.movieguide;

import com.anddev.movieguide.model.Actor;

public class ActorSingleton {

    private static Actor instance = null;

    private ActorSingleton() {

    }

    public static Actor getInstance() {
        return instance;
    }

    public static Actor setInstance(Actor actor) {
        instance = actor;
        return actor;
    }

}
