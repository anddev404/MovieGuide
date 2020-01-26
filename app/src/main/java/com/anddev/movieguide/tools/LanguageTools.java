package com.anddev.movieguide.tools;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageTools {

    private static final String LANGUAGE_POLISH = "pl-PL";
    private static final String LANGUAGE_ENGLISH = "en-EN";

    public static String getLanguage(Activity activity) {

        String country;

        try {

            country = Locale.getDefault().getCountry();

        } catch (Exception e) {
            return LANGUAGE_ENGLISH;
        }
        try {

            if (country.equalsIgnoreCase("PL")) {
                return LANGUAGE_POLISH;
            }

        } catch (Exception e) {

        }


        return LANGUAGE_ENGLISH;
    }
}
