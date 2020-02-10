package com.anddev.movieguide.peopleActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.PopularPeople;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleFragment extends Fragment {

    View rootView;
    Activity activity;
    PopularPeople people;

    @BindView(R.id.people_list_recycler_view)
    RecyclerView peopleListRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_people, container, false);
        activity = getActivity();
        ButterKnife.bind(this, rootView);
        peopleListRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        if (people != null) {
            setData(people);
        }
        return rootView;
    }

    public void setData(final PopularPeople people) {

        this.people = people;

        PeopleAdapter adapter = new PeopleAdapter(activity, people.getResults());
        peopleListRecyclerView.setAdapter(adapter);

//        peopleListRecyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getActivity(), peopleListRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Intent intent = new Intent(getActivity(), ActorActivity_.class);
//                        intent.putExtra("Id", people.getResults().get(position).getId());
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                    }
//
//                })
//        );


    }
}

