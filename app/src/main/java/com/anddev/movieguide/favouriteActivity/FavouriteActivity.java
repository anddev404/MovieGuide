package com.anddev.movieguide.favouriteActivity;

import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.anddev.movieguide.R;
import com.anddev.movieguide.database.DatabaseHelper;
import com.anddev.movieguide.model.Favourite;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.moviesActivity.MoviesFragment;
import com.anddev.movieguide.peopleActivity.PeopleFragment;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.searchEngineActivity.TabsPagerAdapter;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.BackStackManager;
import com.anddev.movieguide.tools.ModelConverter;
import com.anddev.movieguide.tools.NavigationDrawerTools;
import com.anddev.movieguide.tools.StatusBarAndSoftKey;
import com.anddev.movieguide.tvShows.TvShowsFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.List;

@EActivity(R.layout.activity_favourite)
public class FavouriteActivity extends AppCompatActivity implements ActionBar.TabListener, ActionBarTools.OnChangeViewListener {

    //region variables

    FavouriteActivity activity;
    MoviesFragment moviesFragment;
    TvShowsFragment tvShowFragment;
    PeopleFragment peopleFragment;
    ViewPager viewPager;
    TabsPagerAdapter mAdapter;

    Movies movies;
    TvShows tvShows;
    PopularPeople people;

    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

    //endregion

    //region activity
    @AfterViews
    public void onCreate() {
        activity = this;
        BackStackManager.getInstance().addActivity(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.favourite_navigation_draver).setOtherColorForFavouriteButton();
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle(getString(R.string.favourite)).addTabsToView(TabsPagerAdapter.getTabs(), this);
        StatusBarAndSoftKey.changeColor(this);

        List<Favourite> favourite = null;
        favourite = DatabaseHelper.getFavouriteDataDao(activity).queryForAll();
        movies = ModelConverter.favouritesToMovies(favourite, Favourite.FAVOURITE_MOVIE);
        tvShows = ModelConverter.favouritesToTvShows(favourite, Favourite.FAVOURITE_TV_SHOWS);
        people = ModelConverter.favouritesToPopularPeople(favourite, Favourite.FAVOURITE_ACTOR);
        actionBarTools.setTextToTabOfActionBar(0, getResources().getString(R.string.movies) + " (" + movies.getResults().size() + ")");
        actionBarTools.setTextToTabOfActionBar(1, getResources().getString(R.string.tv_shows) + " (" + tvShows.getResults().size() + ")");
        actionBarTools.setTextToTabOfActionBar(2, getResources().getString(R.string.people) + " (" + people.getResults().size() + ")");

        viewPager = (ViewPager) findViewById(R.id.pager_favourite_activity);
        viewPager.setOffscreenPageLimit(3);
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
                if (tvShowFragment == null && arg0 == 1) {
                    String tag = "android:switcher:" + R.id.pager_favourite_activity + ":" + 1;
                    tvShowFragment = (TvShowsFragment) getSupportFragmentManager().findFragmentByTag(tag);

                    if (tvShows != null) {
                        tvShowFragment.setData(tvShows);
                    }
                }
                if (peopleFragment == null && arg0 == 2) {
                    String tag = "android:switcher:" + R.id.pager_favourite_activity + ":" + 2;
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeView(-1);
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

        if (actionBarTools != null) {
            actionBarTools.addSearchEngine(activity, R.menu.action_bar, R.id.app_bar_search, menu,
                    new ActionBarTools.OnSearchEngineListener() {
                        @Override
                        public void onQueryTextSubmit(String query) {
                            actionBarTools.clearFocus();
                            SearchEngineActivity.searchAndOpenResults(query, activity);
                        }
                    });

            actionBarTools.addButtonChangeViewAndSetOnClickListener(activity, R.menu.action_bar, menu);
            actionBarTools.setOnChangeViewListener(this::changeView);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void changeView(int viewType) {
        try {

            Fragment actualFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager_favourite_activity + ":" + viewPager.getCurrentItem());

            if (actualFragment == moviesFragment) {
                moviesFragment.setViewType(viewType);
                moviesFragment.initializeRecyclerViewAndSetAdapter();
            }
            if (actualFragment == tvShowFragment) {
                tvShowFragment.setViewType(viewType);
                tvShowFragment.initializeRecyclerViewAndSetAdapter();
            }
            if (actualFragment == peopleFragment) {
                peopleFragment.setViewType(viewType);
                peopleFragment.initializeRecyclerViewAndSetAdapter();
            }


        } catch (Exception e) {
        }
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
