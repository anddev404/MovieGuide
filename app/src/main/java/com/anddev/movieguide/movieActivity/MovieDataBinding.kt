package com.anddev.movieguide.movieActivity

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.anddev.movieguide.R
import com.anddev.movieguide.tools.ImageTools
import com.squareup.picasso.Picasso

class MovieDataBinding {

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        open fun loadImage(view: ImageView, imageUrl: String) {
            if (imageUrl.isNotEmpty()) {
                Picasso.with(view.getContext())
                        .load(imageUrl)
                        .placeholder(view.getContext().getResources().getDrawable(R.drawable.ic_file_download_black_48dp))
                        .error(view.getContext().getResources().getDrawable(ImageTools.DRAWABLE_FILM_WIDTH))
                        .into(view)
            }

        }

        @BindingAdapter("color", "backgroundAlpha")
        @JvmStatic
        open fun setBackgroundColor(view: LinearLayout, color: Int, alpha: Int) {
            view.setBackgroundColor(color)
            view.getBackground().setAlpha(alpha)

        }

    }
}