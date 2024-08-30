package com.anddev.movieguide.tvShows;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.moviesActivity.CustomImageView;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;
import com.anddev.movieguide.tvShow.TvShowActivity_;
import com.squareup.picasso.Picasso;

public class TvShowsGridViewAdapter extends RecyclerView.Adapter<TvShowsGridViewAdapter.ViewHolder> {

    private Context context;

    private TvShows tvShowsList;

    public TvShowsGridViewAdapter(Context context, TvShows tvShowsList) {
        this.context = context;
        this.tvShowsList = tvShowsList;
    }

    @Override
    public TvShowsGridViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_grid_view_image_list, parent, false);

        return new TvShowsGridViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvShowsGridViewAdapter.ViewHolder holder, int position) {

        try {

            holder.poster.getTextView().setText(tvShowsList.getResults().get(position).getName());
            holder.poster.getImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, TvShowActivity_.class);
                        intent.putExtra("Id", tvShowsList.getResults().get(position).getId());
                        try {
                            intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.poster.getImageButton(),0));

                        } catch (Exception e) {
                        }
                        context.startActivity(intent);
                    } catch (Exception e) {

                    }
                }
            });
            Picasso.with(context)
                    .load(ImageTools.IMAGE_PATH_500px + tvShowsList.getResults().get(position).getPoster_path())
                    .placeholder(context.getResources().getDrawable(R.drawable.download))
                    .error(ImageTools.DRAWABLE_FILM)
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
            return tvShowsList.getResults().size();
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









