package com.anddev.movieguide.tools;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.ColorInt;
import android.view.Window;
import android.view.WindowManager;

import com.anddev.movieguide.R;

public class StatusBarAndSoftKey {

    public static void changeColor(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                window.setNavigationBarColor(activity.getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {

        }
    }

    public static void changeColor(Activity activity, int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(adjustAlpha(color, 0.85f));
                window.setNavigationBarColor(adjustAlpha(color, 0.85f));
            }
        } catch (Exception e) {

        }
    }

    @ColorInt
    private static int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
