package com.anddev.movieguide.database;

import android.app.Activity;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DatabaseSingleton {

    private static DatabaseHelper helper = null;

    private DatabaseSingleton() {
        // Exists only to defeat instantiation.
    }


    public static DatabaseHelper getInstance(Activity activity) {

        if (helper == null) {
            helper = OpenHelperManager.getHelper(activity.getApplicationContext(), DatabaseHelper.class);
        }
        return helper;
    }

}
