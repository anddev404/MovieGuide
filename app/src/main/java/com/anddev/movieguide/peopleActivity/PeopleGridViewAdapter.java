package com.anddev.movieguide.peopleActivity;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddev.movieguide.R;
import com.anddev.movieguide.actorActivity.ActorActivity_;
import com.anddev.movieguide.model.Results;
import com.anddev.movieguide.moviesActivity.CustomImageView;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;
import com.squareup.picasso.Picasso;

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

            holder.poster.getTextView().setText(peopleList.get(position).getName());
            holder.poster.getImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, ActorActivity_.class);
                        intent.putExtra("Id", peopleList.get(position).getId());
                        try {
                            intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.poster.getImageButton(), 0));

                        } catch (Exception e) {
                        }
                        context.startActivity(intent);
                    } catch (Exception e) {

                    }
                }
            });
            Picasso.with(context)
                    .load(ImageTools.IMAGE_PATH_500px + peopleList.get(position).getProfile_path())
                    .placeholder(context.getResources().getDrawable(R.drawable.download))
                    .error(ImageTools.DRAWABLE_PERSON)
                    .into(holder.poster.getImageButton(), new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (holder.poster.getImageButton().getDrawable() != null) {
                                try {

                                    PaletteTools.setBackgroundColorToTextViewFromProminentColorOfImageButton(holder.poster.getTextView(), holder.poster.getImageButton(), context.getResources().getColor(R.color.colorPrimaryDark));
                                    holder.poster.getTextView().setAlpha(0.80f);

                                } catch (Exception e) {

                                }
                            }
                        }

                        @Override
                        public void onError() {
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

        CustomImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (CustomImageView) itemView.findViewById(R.id.imageButton_item_grid_view);
        }
    }
}









