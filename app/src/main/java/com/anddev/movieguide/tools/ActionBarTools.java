package com.anddev.movieguide.tools;

import android.app.Activity;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.anddev.movieguide.R;

public class ActionBarTools {

    AppCompatActivity activity;
    ActionBarTools actionBarTools;
    ActionBar actionBar;

    int changeView = 0;
    public static final int NORMAL_VIEW = 0;
    public static final int GRID1_VIEW = 1;
    public static final int GRID2_VIEW = 2;
    public static final int GRID3_VIEW = 3;
    public static final int GRID4_VIEW = 4;

    public int getTypeOfView() {
        return changeView;
    }

    public void setTypeOfView(int changeView) {
        this.changeView = changeView;
    }

    public ActionBarTools(AppCompatActivity activity) {

        this.activity = activity;
        actionBarTools = this;
        actionBar = activity.getSupportActionBar();
    }

    public ActionBarTools(AppCompatActivity activity, Toolbar toolbar) {

        this.activity = activity;
        actionBarTools = this;
        activity.setSupportActionBar(toolbar);
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

    public ActionBarTools addTabsToViewAndSetListeners(String[] tabs, ViewPager viewPager) {
        try {

            for (String tab_name : tabs) {
                actionBar.addTab(activity.getSupportActionBar().newTab().setText(tab_name)
                        .setTabListener(new ActionBar.TabListener() {
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
                        }));
            }

            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    actionBar.setSelectedNavigationItem(i);

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });


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

    //region change view
    public void addButtonChangeViewAndSetOnClickListener(Activity activity, int idLayoutActionBar, Menu menu) {
        try {
            MenuItem chooseViewButton = menu.findItem(R.id.choose_view_button);

            MenuItem normal = menu.findItem(R.id.row_action_bar);
            MenuItem grid1 = menu.findItem(R.id.grid_view_1_action_bar);
            MenuItem grid2 = menu.findItem(R.id.grid_view_2_action_bar);
            MenuItem grid3 = menu.findItem(R.id.grid_view_3_action_bar);
            MenuItem grid4 = menu.findItem(R.id.grid_view_4_action_bar);


            chooseViewButton.setVisible(true);

            normal.setVisible(true);
            grid1.setVisible(true);
            grid2.setVisible(true);
            grid3.setVisible(true);
            grid4.setVisible(true);

            normal.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    changeView = 0;
                    Log.d("ACTION_BAR_TOOLS", "change view: normal");

                    if (changeViewListener != null) {
                        changeViewListener.changeView(changeView);
                    }
                    return false;
                }
            });

            grid1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    changeView = 1;
                    Log.d("ACTION_BAR_TOOLS", "change view: grid 1");

                    if (changeViewListener != null) {
                        changeViewListener.changeView(changeView);
                    }
                    return false;
                }
            });

            grid2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    changeView = 2;
                    Log.d("ACTION_BAR_TOOLS", "change view: grid 2");

                    if (changeViewListener != null) {
                        changeViewListener.changeView(changeView);
                    }
                    return false;
                }
            });

            grid3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    changeView = 3;
                    Log.d("ACTION_BAR_TOOLS", "change view: grid 3");

                    if (changeViewListener != null) {
                        changeViewListener.changeView(changeView);
                    }
                    return false;
                }
            });

            grid4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    changeView = 4;
                    Log.d("ACTION_BAR_TOOLS", "change view: grid 4");

                    if (changeViewListener != null) {
                        changeViewListener.changeView(changeView);
                    }
                    return false;
                }
            });
        } catch (Exception e) {

        }
    }

    //////////////////////////////
    OnChangeViewListener changeViewListener;

    public void setOnChangeViewListener(OnChangeViewListener onChangeViewListener) {
        changeViewListener = onChangeViewListener;
    }

    public interface OnChangeViewListener {
        void changeView(int viewType);

    }
    //////////////////////////////
    //endregion

    //region search engine
    SearchView searchView;
    MenuItem searchItem;

    public void clearFocus() {
        if (searchView != null) {
            searchView.clearFocus();
        }
    }

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
