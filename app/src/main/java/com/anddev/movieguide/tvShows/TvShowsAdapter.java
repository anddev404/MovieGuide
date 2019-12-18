package com.anddev.movieguide.tvShows;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.ResultsMovie;
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.moviesActivity.MoviesAdapter;
import com.anddev.movieguide.tools.ImageTools;
import com.makeramen.roundedimageview.RoundedImageView;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder> {

    private TvShowsAdapter adapter;
    private Activity activity;

    private TvShows tvShowsList;

    public TvShowsAdapter(Activity activity, TvShows tvShowsList) {
        this.activity = activity;
        this.tvShowsList = tvShowsList;
    }

    @Override
    public TvShowsAdapter.TvShowsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.row_movies_list, parent, false);

        return new TvShowsAdapter.TvShowsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvShowsAdapter.TvShowsViewHolder holder, int position) {

        TvShows.Results result = tvShowsList.getResults().get(position);

        holder.titleTextView.setText(result.getName());
        holder.releaseDateTextView.setText(result.getFirst_air_date());
        holder.averageVoteTextView.setText(Double.toString(result.getVote_average()));
        ImageTools.getImageFromInternet(activity, ImageTools.IMAGE_PATH_500px + result.getPoster_path(), holder.moviesImageView, ImageTools.DRAWABLE_PERSON);


    }


    @Override
    public int getItemCount() {
        return tvShowsList.getResults().size();
    }


    class TvShowsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView releaseDateTextView;
        TextView averageVoteTextView;
        RoundedImageView moviesImageView;


        public TvShowsViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.row_title_movies_list_textView);
            releaseDateTextView = itemView.findViewById(R.id.row_release_date_movies_list_textView);
            averageVoteTextView = itemView.findViewById(R.id.row_average_vote_date_movies_list_textView);
            moviesImageView = itemView.findViewById(R.id.row_image_movies_list_imageView);

        }
    }
}


