package com.anddev.movieguide.moviesActivity;

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
import com.anddev.movieguide.model.Genre;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.ResultsMovie;
import com.anddev.movieguide.movieActivity.MovieActvity_;
import com.anddev.movieguide.tools.DateTools;
import com.anddev.movieguide.tools.FavouriteTools;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;
import com.anddev.movieguide.trailersActivity.TrailersActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private MoviesAdapter adapter;
    private Activity activity;

    private Movies moviesList;
    private Genre genres;

    FavouriteTools favouriteTools;

    public MoviesAdapter(Activity activity, Movies moviesList) {
        this.activity = activity;
        this.moviesList = moviesList;
        favouriteTools = new FavouriteTools(activity);
    }

    public MoviesAdapter(Activity activity, Movies moviesList, Genre genres) {
        this.activity = activity;
        this.moviesList = moviesList;
        this.genres = genres;
        favouriteTools = new FavouriteTools(activity);
    }

    public void setGenres(Genre genres) {

        if (this.genres == null) {
            this.genres = genres;
            notifyDataSetChanged();
        }
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

        holder.titleTextView.setText(result.getTitle() + " " + DateTools.getOnlyYear(result.getRelease_date()));
        holder.averageVoteTextView.setText(Double.toString(result.getVote_average()));
        ImageTools.getImageFromInternet(activity, ImageTools.IMAGE_PATH_500px + result.getPoster_path(), holder.moviesImageView, ImageTools.DRAWABLE_FILM);
        if (genres != null) {
            String s = "";

            try {
                s = genresToString(result.getGenre_ids(), genres);
                holder.releaseDateTextView.setText(s);
            } catch (Exception e) {

            }
        }
        //holder.releaseDateTextView.setText(holder.releaseDateTextView.getText() + "\n" ));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, MovieActvity_.class);
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

                    TrailersActivity.goToActivity(activity, result.getTitle() + " " + DateTools.getOnlyYear(result.getRelease_date()));

                } catch (Exception e) {
                    try {

                        TrailersActivity.goToActivity(activity, result.getTitle());

                    } catch (Exception ee) {

                        Toast.makeText(activity, activity.getString(R.string.no_results), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        try {
            favouriteTools.manageFavouriteButton(holder.starImageButton, result.getId(), Favourite.FAVOURITE_MOVIE, result.getTitle() + " " + DateTools.getOnlyYear(result.getRelease_date()), "", Double.toString(result.getVote_average()), result.getPoster_path());
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


    class MoviesViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView releaseDateTextView;
        TextView averageVoteTextView;
        RoundedImageView moviesImageView;
        ImageButton starImageButton;
        ImageButton youtubeTrailerButton;
        LinearLayout layout;

        public MoviesViewHolder(View itemView) {
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

    public String getGenre(int id, Genre genres) {
        String genreString = "";
        try {


            for (Genre.Genres g : genres.getGenres()) {
                if (g.getId() == id) {
                    return g.getName();
                }
            }
        } catch (Exception e) {

        }
        return genreString;
    }

    public String genresToString(List<Integer> listGenresId, Genre genres) {
        String genresString = "";

        try {

            if (genres.getGenres() != null && genres.getGenres().size() > 0) {
                genresString = getGenre(listGenresId.get(0), genres);

                for (int i = 1; i < listGenresId.size(); i++) {
                    genresString = genresString + ", " + getGenre(listGenresId.get(i), genres);
                }
            }
            return genresString;


        } catch (Exception e) {
            return "";
        }
    }
}


