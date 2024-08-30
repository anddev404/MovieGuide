package com.anddev.movieguide.tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class BackStackManager {

    private int maxActivitiesInMemory = 4;
    List<Activity> activities = new ArrayList();

    public void addActivity(Activity activity) {
        activities.add(activity);

        if (activities.size() > maxActivitiesInMemory) {
            activities.get(0).finish();
            activities.remove(0);
        }
    }

    //region singleton
    private static BackStackManager instance;

    private BackStackManager() {
    }

    public static BackStackManager getInstance() {
        if (instance == null) {
            instance = new BackStackManager();
        }
        return instance;
    }
    //endregion

}
