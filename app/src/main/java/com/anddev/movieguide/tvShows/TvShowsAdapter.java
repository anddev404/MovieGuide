package com.anddev.movieguide.tvShows;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Favourite;
import com.anddev.movieguide.model.TvShows;
import com.anddev.movieguide.tools.DateTools;
import com.anddev.movieguide.tools.FavouriteTools;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;
import com.anddev.movieguide.trailersActivity.TrailersActivity;
import com.anddev.movieguide.tvShow.TvShowActivity_;
import com.makeramen.roundedimageview.RoundedImageView;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder> {

    private TvShowsAdapter adapter;
    private Activity activity;

    private TvShows tvShowsList;

    FavouriteTools favouriteTools;


    public TvShowsAdapter(Activity activity, TvShows tvShowsList) {
        this.activity = activity;
        this.tvShowsList = tvShowsList;
        favouriteTools = new FavouriteTools(activity);
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
        ImageTools.getImageFromInternet(activity, ImageTools.IMAGE_PATH_500px + result.getPoster_path(), holder.moviesImageView, ImageTools.DRAWABLE_TV_SHOW);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, TvShowActivity_.class);
                intent.putExtra("Id", result.getId());
                try {
                    intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.moviesImageView, 0));

                } catch (Exception e) {
                }
                activity.startActivity(intent);

            }
        });

        holder.youtubeTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    TrailersActivity.goToActivity(activity, result.getName() + " " + DateTools.getOnlyYear(result.getFirst_air_date()));

                } catch (Exception e) {
                    try {

                        TrailersActivity.goToActivity(activity, result.getName());

                    } catch (Exception ee) {

                        Toast.makeText(activity, activity.getString(R.string.no_results), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        try {
            favouriteTools.manageFavouriteButton(holder.starImageButton, result.getId(), Favourite.FAVOURITE_TV_SHOWS, result.getName() + " " + DateTools.getOnlyYear(result.getFirst_air_date()), "", Double.toString(result.getVote_average()), result.getPoster_path());
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


    class TvShowsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView releaseDateTextView;
        TextView averageVoteTextView;
        RoundedImageView moviesImageView;
        ImageButton starImageButton;
        ImageButton youtubeTrailerButton;
        LinearLayout layout;


        public TvShowsViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.row_title_movies_list_textView);
            releaseDateTextView = itemView.findViewById(R.id.row_release_date_movies_list_textView);
            averageVoteTextView = itemView.findViewById(R.id.row_average_vote_date_movies_list_textView);
            moviesImageView = itemView.findViewById(R.id.row_image_movies_list_imageView);
            starImageButton = itemView.findViewById(R.id.row_favourite_movies_list_imageButton);
            youtubeTrailerButton = itemView.findViewById(R.id.youtube_trailer_list);
            layout = itemView.findViewById(R.id.row_movies_list_layout);

        }
    }
}


