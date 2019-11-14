package com.anddev.movieguide.moviesActivity;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.ResultsMovie;
import com.anddev.movieguide.tools.ImageTools;
import com.makeramen.roundedimageview.RoundedImageView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private MoviesAdapter adapter;
    private Activity activity;

    private Movies moviesList;

    public MoviesAdapter(Activity activity, Movies moviesList) {
        this.activity = activity;
        this.moviesList = moviesList;
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.row_movies_list, parent, false);

        return new MoviesAdapter.MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MoviesViewHolder holder, int position) {

        ResultsMovie result = moviesList.getResults().get(position);

        holder.titleTextView.setText(result.getTitle());
        holder.releaseDateTextView.setText(result.getRelease_date());
        holder.averageVoteTextView.setText(Double.toString(result.getVote_average()));
        ImageTools.getImageFromInternet(activity, ImageTools.IMAGE_PATH_500px + result.getPoster_path(), holder.moviesImageView, ImageTools.DRAWABLE_PERSON);


    }


    @Override
    public int getItemCount() {
        return moviesList.getResults().size();
    }


    class MoviesViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView releaseDateTextView;
        TextView averageVoteTextView;
        RoundedImageView moviesImageView;


        public MoviesViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.row_title_movies_list_textView);
            releaseDateTextView = itemView.findViewById(R.id.row_release_date_movies_list_textView);
            averageVoteTextView = itemView.findViewById(R.id.row_average_vote_date_movies_list_textView);
            moviesImageView = itemView.findViewById(R.id.row_image_movies_list_imageView);

        }
    }
}


