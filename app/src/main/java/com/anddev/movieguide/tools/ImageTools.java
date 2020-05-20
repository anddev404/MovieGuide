package com.anddev.movieguide.tools;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.anddev.movieguide.R;
import com.squareup.picasso.Picasso;

public class ImageTools {

    public static final String IMAGE_PATH_500px = "https://image.tmdb.org/t/p/w500";
    public static final String IMAGE_PATH_ORYGINAL = "https://image.tmdb.org/t/p/original";

    public static final int DRAWABLE_FILM = R.drawable.ic_local_movies_black_24dp;
    public static final int DRAWABLE_FILM_WIDTH = R.drawable.ic_local_movies_black_48dp;

    public static final int DRAWABLE_TV_SHOW = R.drawable.tv_button;

    public static final int DRAWABLE_PERSON = R.drawable.person;

    public static void getImageFromInternet(Activity activity, String url, ImageView imageView, int drawableError) {

        Picasso.with(activity)
                .load(url)
                .placeholder(activity.getResources().getDrawable(R.drawable.download))
                .error(activity.getResources().getDrawable(drawableError))
                .into(imageView);

    }

    public static void getImageFromInternet(Context context, String url, ImageView imageView, int drawableError) {

        Picasso.with(context)
                .load(url)
                .placeholder(context.getResources().getDrawable(R.drawable.download))
                .error(context.getResources().getDrawable(drawableError))
                .into(imageView);

    }

    public static void getWideImageFromInternet(Activity activity, String url, ImageView imageView, int drawableError) {

        Picasso.with(activity)
                .load(url)
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_file_download_black_48dp))
                .error(activity.getResources().getDrawable(drawableError))
                .into(imageView);

    }
}
