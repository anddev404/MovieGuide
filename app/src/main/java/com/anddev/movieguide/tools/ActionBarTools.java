package com.anddev.movieguide.tools;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.anddev.movieguide.R;

public class ActionBarTools {

    AppCompatActivity activity;
    ActionBarTools actionBarTools;

    public ActionBarTools(AppCompatActivity activity) {

        this.activity = activity;
        actionBarTools = this;
    }

    public ActionBarTools addMenuButton() {

        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_button);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return actionBarTools;

    }

    public ActionBarTools setTitle(String string) {

        activity.getSupportActionBar().setTitle(string);

        return actionBarTools;

    }

    public ActionBarTools addTabsToView(String[] tabs, ActionBar.TabListener tabListener) {
        try {

            for (String tab_name : tabs) {
                activity.getSupportActionBar().addTab(activity.getSupportActionBar().newTab().setText(tab_name)
                        .setTabListener(tabListener));
            }

            activity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        } catch (Exception e) {

        }

        return actionBarTools;
    }

}
