package com.anddev.movieguide.tools;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.anddev.movieguide.R;

public class ActionBarTools {

    AppCompatActivity activity;
    ActionBarTools actionBarTools;
    ActionBar actionBar;

    public ActionBarTools(AppCompatActivity activity) {

        this.activity = activity;
        actionBarTools = this;
        actionBar = activity.getSupportActionBar();
    }

    public ActionBarTools addMenuButton() {

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_button);
        actionBar.setDisplayHomeAsUpEnabled(true);

        return actionBarTools;

    }

    public ActionBarTools setTitle(String string) {

        actionBar.setTitle(string);

        return actionBarTools;

    }

    public ActionBarTools addTabsToView(String[] tabs, ActionBar.TabListener tabListener) {
        try {

            for (String tab_name : tabs) {
                actionBar.addTab(activity.getSupportActionBar().newTab().setText(tab_name)
                        .setTabListener(tabListener));
            }

            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        } catch (Exception e) {

        }

        return actionBarTools;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

}
