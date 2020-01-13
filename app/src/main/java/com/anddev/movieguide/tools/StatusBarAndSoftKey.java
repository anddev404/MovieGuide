package com.anddev.movieguide.tools;

import android.app.Activity;
import android.os.Build;
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
                window.setNavigationBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            }
        } catch (Exception e) {

        }
    }
}
