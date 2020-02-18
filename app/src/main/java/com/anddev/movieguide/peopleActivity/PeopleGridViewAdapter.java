package com.anddev.movieguide.peopleActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.anddev.movieguide.R;
import com.anddev.movieguide.actorActivity.ActorActivity_;
import com.anddev.movieguide.model.Results;
import com.anddev.movieguide.tools.ImageTools;

import java.util.List;

public class PeopleGridViewAdapter extends RecyclerView.Adapter<PeopleGridViewAdapter.ViewHolder> {

    private Context context;

    private List<Results> peopleList;

    public PeopleGridViewAdapter(Context context, List<Results> peopleList) {
        this.context = context;
        this.peopleList = peopleList;
    }

    @Override
    public PeopleGridViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_grid_view_image_list, parent, false);

        return new PeopleGridViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeopleGridViewAdapter.ViewHolder holder, int position) {

        try {

            ImageTools.getImageFromInternet(context, ImageTools.IMAGE_PATH_500px + peopleList.get(position).getProfile_path(), holder.poster, ImageTools.DRAWABLE_PERSON);
            holder.poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, ActorActivity_.class);
                        intent.putExtra("Id", peopleList.get(position).getId());
                        context.startActivity(intent);
                    } catch (Exception e) {

                    }
                }
            });

        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        try {
            return peopleList.size();
        } catch (Exception e) {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton poster;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (ImageButton) itemView.findViewById(R.id.imageButton_item_grid_view);
        }
    }
}









