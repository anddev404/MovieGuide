package com.anddev.movieguide.favouriteActivity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.moviesActivity.MoviesFragment;
import com.anddev.movieguide.peopleActivity.PeopleFragment;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.searchEngineActivity.TabsPagerAdapter;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_favourite)
public class FavouriteActivity extends AppCompatActivity implements ActionBar.TabListener {

    //region variables

    FavouriteActivity activity;
    MoviesFragment moviesFragment;
    PeopleFragment peopleFragment;
    ViewPager viewPager;
    TabsPagerAdapter mAdapter;

    Movies movies;
    PopularPeople people;

    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

    //endregion

    //region activity
    @AfterViews
    public void onCreate() {
        activity = this;
        navigationDrawer = new NavigationDrawerTools(activity, R.id.favourite_navigation_draver).setOtherColorForFavouriteButton();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(getString(R.string.favourite)).addTabsToView(TabsPagerAdapter.getTabs(), this);
        StatusBarAndSoftKey.changeColor(this);

        viewPager = (ViewPager) findViewById(R.id.pager_favourite_activity);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

                if (moviesFragment == null && arg0 == 0) {
                    String tag = "android:switcher:" + R.id.pager_favourite_activity + ":" + 0;
                    moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    if (movies != null) {
                        moviesFragment.setData(movies);
                    }
                }

                if (peopleFragment == null && arg0 == 1) {
                    String tag = "android:switcher:" + R.id.pager_favourite_activity + ":" + 1;
                    peopleFragment = (PeopleFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    if (people != null) {
                        peopleFragment.setData(people);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

        });
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

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
