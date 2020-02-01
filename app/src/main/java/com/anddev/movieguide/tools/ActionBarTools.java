package com.anddev.movieguide.tools;

import android.app.Activity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    public ActionBarTools addBackButton() {

        actionBar.setHomeAsUpIndicator(R.drawable.back_button);
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

    public ActionBarTools setTextToTabOfActionBar(int position, String string) {
        try {

            actionBar.getTabAt(position).setText(string);

        } catch (Exception e) {

        }

        return actionBarTools;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

    //region search engine
    SearchView searchView;
    MenuItem searchItem;

    public void addSearchEngine(Activity activity, int idLayoutActionBar, int idSearchView, Menu menu, OnSearchEngineListener onSearchEngineListener) {
        try {

            mListener = onSearchEngineListener;

            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(idLayoutActionBar, menu);

            searchItem = menu.findItem(idSearchView);
            searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint(activity.getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    if (mListener != null) {
                        mListener.onQueryTextSubmit(query);
                    }

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {

                    return false;
                }
            });
        } catch (Exception e) {

        }
    }

    public void closeSearchEngineIfOpen() {

        try {
            searchView.setInputType(InputType.TYPE_NULL);
            MenuItemCompat.collapseActionView(searchItem);
        } catch (Exception e) {

        }

    }

    //////////////////////////////
    private OnSearchEngineListener mListener;

    public interface OnSearchEngineListener {
        void onQueryTextSubmit(String query);
    }
//endregion
}
