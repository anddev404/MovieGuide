package com.anddev.movieguide.peopleActivity;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.PreferenceTools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleFragment extends Fragment {

    View rootView;
    Activity activity;
    PopularPeople people;
    PeopleAdapter adapter;
    PeopleGridViewAdapter adapterGridView;

    @BindView(R.id.people_list_recycler_view)
    public RecyclerView peopleListRecyclerView;
    int viewType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_people, container, false);
        activity = getActivity();
        ButterKnife.bind(this, rootView);
        PreferenceTools.initializePreferenceLibrary(activity);

        if (people != null) {
            initializeRecyclerViewAndSetAdapter();
        }
        return rootView;
    }

    public void setData(final PopularPeople people) {

        this.people = people;

        initializeRecyclerViewAndSetAdapter();
    }

    public void addData(PopularPeople people) {

        try {
            this.people.getResults().addAll(people.getResults());
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void setViewType(int viewType) {
        if (viewType >= 0) {
            this.viewType = viewType;
            PreferenceTools.saveTypeOfView(viewType, PreferenceTools.SAVE_PEOPLE, activity);
        }
    }

    public void initializeRecyclerViewAndSetAdapter() {
        Log.d("PEOPLE_FRAGMENT", "initialize view");
        viewType = PreferenceTools.getTypeOfView(ActionBarTools.NORMAL_VIEW, PreferenceTools.SAVE_PEOPLE, activity);

        try {
            if (viewType == ActionBarTools.GRID1_VIEW) {
                peopleListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
                adapterGridView = new PeopleGridViewAdapter(activity, people.getResults());
                peopleListRecyclerView.setAdapter(adapterGridView);

            } else if (viewType == ActionBarTools.GRID2_VIEW) {
                peopleListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
                adapterGridView = new PeopleGridViewAdapter(activity, people.getResults());
                peopleListRecyclerView.setAdapter(adapterGridView);


            } else if (viewType == ActionBarTools.GRID3_VIEW) {
                peopleListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
                adapterGridView = new PeopleGridViewAdapter(activity, people.getResults());
                peopleListRecyclerView.setAdapter(adapterGridView);


            } else if (viewType == ActionBarTools.GRID4_VIEW) {
                peopleListRecyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
                adapterGridView = new PeopleGridViewAdapter(activity, people.getResults());
                peopleListRecyclerView.setAdapter(adapterGridView);


            } else {
                peopleListRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                adapter = new PeopleAdapter(activity, people.getResults());
                peopleListRecyclerView.setAdapter(adapter);

            }

        } catch (Exception e) {

        }


    }


}

