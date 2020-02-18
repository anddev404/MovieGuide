package com.anddev.movieguide.moviesActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Genre;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.movieActivity.MovieActvity_;
import com.anddev.movieguide.tools.ImageTools;

public class MoviesGridViewAdapter extends RecyclerView.Adapter<MoviesGridViewAdapter.ViewHolder> {

    private Context context;

    private Movies moviesList;
    private Genre genres;

    public MoviesGridViewAdapter(Context context, Movies movies) {
        this.context = context;
        this.moviesList = movies;
    }

    public MoviesGridViewAdapter(Context context, Movies moviesList, Genre genres) {
        this.context = context;
        this.moviesList = moviesList;
        this.genres = genres;
    }

    public void setGenres(Genre genres) {

        if (this.genres == null) {
            this.genres = genres;
            notifyDataSetChanged();
        }
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

            ImageTools.getImageFromInternet(context, ImageTools.IMAGE_PATH_500px + moviesList.getResults().get(position).getPoster_path(), holder.poster, ImageTools.DRAWABLE_FILM);
            holder.poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, MovieActvity_.class);
                        intent.putExtra("Id", moviesList.getResults().get(position).getId());
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
            return moviesList.getResults().size();
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
