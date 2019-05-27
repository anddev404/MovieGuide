package com.anddev.movieguide.tools;

import android.app.Activity;
import android.widget.ImageView;

import com.anddev.movieguide.R;
import com.squareup.picasso.Picasso;

public class ImageTools {

    public static final int DRAWABLE_FILM = R.drawable.ic_local_movies_black_24dp;
    public static final int DRAWABLE_PERSON = R.drawable.person;

    public static void getImageFromInternet(Activity activity, String url, ImageView imageView, int drawableError) {

        Picasso.with(activity)
                .load(url)
                .placeholder(activity.getResources().getDrawable(R.drawable.download))
                .error(activity.getResources().getDrawable(drawableError))
                .into(imageView);

    }
}
