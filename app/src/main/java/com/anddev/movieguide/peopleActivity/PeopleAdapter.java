package com.anddev.movieguide.peopleActivity;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.actorActivity.ActorActivity_;
import com.anddev.movieguide.model.Favourite;
import com.anddev.movieguide.model.KnownForPopular;
import com.anddev.movieguide.model.Results;
import com.anddev.movieguide.tools.FavouriteTools;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {
    private PeopleAdapter adapter;


    private Activity context;
    private FavouriteTools favouriteTools;
    private List<Results> peopleList;

    public PeopleAdapter(Activity context, List<Results> peopleList) {
        this.context = context;
        this.peopleList = peopleList;
        favouriteTools = new FavouriteTools(context);
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
        holder.movieTextView.setText(getMovieOfActor(result.getKnownForPopular()));
        ImageTools.getImageFromInternet(context, "https://image.tmdb.org/t/p/w500/" + peopleList.get(position).getProfile_path(), holder.peopleImageView, ImageTools.DRAWABLE_PERSON);

        try {
            favouriteTools.manageFavouriteButton(holder.starButton, result.getId(), Favourite.FAVOURITE_ACTOR, result.getName(), "", "", result.getProfile_path());
        } catch (Exception e) {
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ActorActivity_.class);
                intent.putExtra("Id", peopleList.get(position).getId());
                try {
                    intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.peopleImageView, 0));

                } catch (Exception e) {
                }
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        try {
            return peopleList.size();
        } catch (Exception e) {
            return 0;
        }
    }


    class PeopleViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView movieTextView;
        RoundedImageView peopleImageView;
        ImageButton starButton;
        LinearLayout layout;


        public PeopleViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.row_name_people_list_textView);
            movieTextView = itemView.findViewById(R.id.row_movie_people_list_textView);
            peopleImageView = itemView.findViewById(R.id.row_people_list_imageView);
            starButton = itemView.findViewById(R.id.row_favourite_people_list_imageButton);
            layout = itemView.findViewById(R.id.row_people_list_layout);

        }
    }

    public String getMovieOfActor(List<KnownForPopular> listOfMovie) {

        String movie = "";
        try {
            if (listOfMovie.get(0).getTitle() != null) {
                if (listOfMovie.get(0).getTitle().length() > 0) {
                    movie = listOfMovie.get(0).getTitle();

                }

            }


        } catch (Exception e) {
            return "";

        }
        for (int i = 1; i < listOfMovie.size(); i++) {
            if (listOfMovie.get(i).getTitle() != null) {
                if (listOfMovie.get(i).getTitle().length() > 0) {

                    if (movie.length() > 0) {
                        movie = movie + ", " + listOfMovie.get(i).getTitle();

                    } else {
                        movie = listOfMovie.get(i).getTitle();

                    }

                }

            }
        }

        return movie;

    }
}

