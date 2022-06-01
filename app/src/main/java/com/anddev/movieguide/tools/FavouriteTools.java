package com.anddev.movieguide.tools;

import android.app.Activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.anddev.movieguide.R;
import com.anddev.movieguide.database.DatabaseHelper;
import com.anddev.movieguide.model.Favourite;

import java.util.ArrayList;
import java.util.List;

public class FavouriteTools {

    List<Favourite> favourites;
    Activity activity;

    public FavouriteTools(Activity activity) {
        this.activity = activity;
        favourites = new ArrayList<>();
        updateList();
    }

    public void manageFavouriteButton(ImageButton starImageButton, int id, int type, String name, String description, String rating, String posterPath) {
        try {
            changeMovieStarIconOnButton(starImageButton, id);

            starImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {


                        if (addOrRemoveFromFavourite(id, type, name, description, rating, posterPath)) {
                            changeStarIconOnButton(starImageButton, true);
                        } else {
                            changeStarIconOnButton(starImageButton, false);

                        }
                    } catch (Exception e) {

                    }
                }
            });

        } catch (Exception e) {
        }
    }

    public void manageFavouriteFloatingActionButtom(FloatingActionButton starImageButton, int id, int type, String name, String description, String rating, String posterPath) {
        try {
            changeMovieStarIconOnButton(starImageButton, id);

            starImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {


                        if (addOrRemoveFromFavourite(id, type, name, description, rating, posterPath)) {
                            changeStarIconOnButton(starImageButton, true);
                            starImageButton.hide();
                            starImageButton.show();

                        } else {
                            changeStarIconOnButton(starImageButton, false);
                            starImageButton.hide();
                            starImageButton.show();
                        }
                    } catch (Exception e) {

                    }
                }
            });

        } catch (Exception e) {
        }
    }

    private FavouriteTools updateList() {
        try {
            favourites = DatabaseHelper.getFavouriteDataDao(activity).queryForAll();

        } catch (Exception e) {
            favourites = new ArrayList<>();
        }
        Log.d("favourite size= " + favourites.size(), "FAVOURITE_TOOLS");
        return this;
    }

    private boolean addOrRemoveFromFavourite(int id, int type, String name, String description, String rating, String posterPath) {
        try {
            updateList();
            for (Favourite f : favourites) {
                if (f.favouriteId == id) {
                    DatabaseHelper.getFavouriteDataDao(activity).delete(f);
                    Log.d("remove from favourite ", "FAVOURITE_TOOLS");
                    updateList();
                    return false;
                }
            }
            DatabaseHelper.getFavouriteDataDao(activity).create(new Favourite(id, type, name, description, rating, posterPath));
            Log.d("add to favourite", "FAVOURITE_TOOLS");
            updateList();
            return true;

        } catch (Exception e) {
        }

        return false;
    }

    private void changeStarIconOnButton(ImageButton imageButton, boolean isFavourite) {
        if (imageButton != null) {
            if (isFavourite) {
                imageButton.setImageDrawable(activity.getResources().getDrawable(R.mipmap.star_big_on));
            } else {
                imageButton.setImageDrawable(activity.getResources().getDrawable(R.mipmap.star_big_off));

            }
        }
    }

    private void changeMovieStarIconOnButton(ImageButton imageButton, int idMovie) {

        try {
            for (Favourite f : favourites) {
                if (f.favouriteId == idMovie) {
                    imageButton.setImageDrawable(activity.getResources().getDrawable(R.mipmap.star_big_on));
                    return;
                }
            }

            imageButton.setImageDrawable(activity.getResources().getDrawable(R.mipmap.star_big_off));

        } catch (Exception e) {
        }
    }
}
