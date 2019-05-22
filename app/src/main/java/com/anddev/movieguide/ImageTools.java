package com.anddev.movieguide;

import android.app.Activity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageTools {

    public static void getImageFromInternet(Activity activity, String url, ImageView imageView) {

        Picasso.with(activity)
                .load(url)
                .placeholder(activity.getResources().getDrawable(R.drawable.download))
                .error(activity.getResources().getDrawable(R.drawable.person))
                .into(imageView);

    }
}
