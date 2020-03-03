package com.anddev.movieguide.moviesActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Genre;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.movieActivity.MovieActvity_;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;
import com.squareup.picasso.Picasso;

public class MoviesGridViewAdapter extends RecyclerView.Adapter<MoviesGridViewAdapter.ViewHolder> {

    private Context context;

    private Movies moviesList;

    public MoviesGridViewAdapter(Context context, Movies movies) {
        this.context = context;
        this.moviesList = movies;
    }

    @Override
    public MoviesGridViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_grid_view_image_list, parent, false);

        return new MoviesGridViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesGridViewAdapter.ViewHolder holder, int position) {

        try {

            holder.poster.getTextView().setText(moviesList.getResults().get(position).getTitle());
            holder.poster.getImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieActvity_.class);
                    intent.putExtra("Id", moviesList.getResults().get(position).getId());

                    try {
                        intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.poster.getImageButton(), 0));

                    } catch (Exception e) {
                    }

                    context.startActivity(intent);
                }
            });

            Picasso.with(context)
                    .load(ImageTools.IMAGE_PATH_500px + moviesList.getResults().get(position).getPoster_path())
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
            return moviesList.getResults().size();
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
