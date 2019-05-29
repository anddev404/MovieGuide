package com.anddev.movieguide.peopleActivity;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Results;
import com.anddev.movieguide.tools.ImageTools;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {
    private PeopleAdapter adapter;


    private Activity context;

    private List<Results> peopleList;

    public PeopleAdapter(Activity context, List<Results> peopleList) {
        this.context = context;
        this.peopleList = peopleList;
    }

    @Override
    public PeopleAdapter.PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_people_list, parent, false);

        return new PeopleAdapter.PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeopleAdapter.PeopleViewHolder holder, int position) {

        Results result = peopleList.get(position);

        holder.nameTextView.setText(result.getName());
        ImageTools.getImageFromInternet(context, "https://image.tmdb.org/t/p/w500/" + peopleList.get(position).getProfile_path(), holder.peopleImageView, ImageTools.DRAWABLE_PERSON);


    }


    @Override
    public int getItemCount() {
        return peopleList.size();
    }


    class PeopleViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView peopleImageView;


        public PeopleViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.row_name_people_list_textView);
            peopleImageView = itemView.findViewById(R.id.row_people_list_imageView);

        }
    }
}

