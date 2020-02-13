package com.anddev.movieguide.favouriteActivity;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.anddev.movieguide.R;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_favourite)
public class FavouriteActivity extends AppCompatActivity {

    //region variables

    FavouriteActivity activity;

    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

    //endregion

    //region activity
    @AfterViews
    public void onCreate() {
        activity = this;
        navigationDrawer = new NavigationDrawerTools(activity, R.id.favourite_navigation_draver).setOtherColorForFavouriteButton();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(getString(R.string.favourite));
        StatusBarAndSoftKey.changeColor(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        actionBarTools.closeSearchEngineIfOpen();

        if (navigationDrawer != null) {
            if (navigationDrawer.closeNavigationDrawerIfOpen()) {
            }
        }
    }

    //endregion

    //region actionBar buttons

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        actionBarTools.addSearchEngine(activity, R.menu.action_bar, R.id.app_bar_search, menu,
                new ActionBarTools.OnSearchEngineListener() {
                    @Override
                    public void onQueryTextSubmit(String query) {
                        actionBarTools.clearFocus();
                        SearchEngineActivity.searchAndOpenResults(query, activity);

                    }
                });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:

                navigationDrawer.openOrCloseNavigationDrawer();

                break;
        }
        return true;
    }

    //endregion

    //region back Button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if (navigationDrawer != null) {
                if (navigationDrawer.closeNavigationDrawerIfOpen()) {
                    return true;
                }

            }

            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }
    //endregion
}
