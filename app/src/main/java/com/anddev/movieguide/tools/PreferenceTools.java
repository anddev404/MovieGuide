package com.anddev.movieguide.tools;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

public class PreferenceTools {

    public static final String SAVE_MOVIES = "movies";
    public static final String SAVE_TV_SHOWS = "tv_shows";
    public static final String SAVE_PEOPLE = "people";

    private static final String PORTRAIN = "_p";
    private static final String LANDSCAPE = "_l";


    public static void initializePreferenceLibrary(Context context) {
        new com.pixplicity.easyprefs.library.Prefs.Builder()
                .setContext(context)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(context.getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        Log.d("PREFERENCE_TOOLS", "initialize preference");

    }

    public static boolean saveTypeOfView(int typeOfView, String clazz, Context context) {

        try {
            Prefs.putInt(clazz + getOrientation(context), typeOfView);
            Log.d("PREFERENCE_TOOLS", "save " + typeOfView);

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public static int getTypeOfView(int defaultValue, String clazz, Context context) {

        try {
            int i = Prefs.getInt(clazz + getOrientation(context), defaultValue);
            Log.d("PREFERENCE_TOOLS", "get " + i);
            return i;
        } catch (Exception e) {

            return defaultValue;
        }
    }

    private static String getOrientation(Context context) {
        try {
            int orientation = context.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d("PREFERENCE_TOOLS", "orientation: landsape");
                return LANDSCAPE;
            } else {
                Log.d("PREFERENCE_TOOLS", "orientation: portrain");
                return PORTRAIN;
            }
        } catch (Exception e) {
            Log.d("PREFERENCE_TOOLS", "orientation: portrain(error)");
            return PORTRAIN;
        }
    }
}
