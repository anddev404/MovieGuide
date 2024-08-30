package com.anddev.movieguide.tools;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.palette.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedDrawable;

public class PaletteTools {

    public static void setBackgroundColorToTextViewFromProminentColorOfImageButton(TextView textView, ImageView imageView, int defautColor) {
        try {
            textView.setBackgroundColor(getMutedColorFromPalette(createPaletteSync(getBitmapFromImageView(imageView)), defautColor));
        } catch (Exception e) {

        }
    }

    public static int getColorFromImageButton(ImageView imageView, int defautColor) {
        try {
            Bitmap bitmap = getBitmapFromImageView(imageView);
            Palette palette = createPaletteSync(bitmap);
            int color = palette.getDarkVibrantColor(defautColor);
            if (color == defautColor) {
                color = palette.getDominantColor(defautColor);
            }
            return color;
        } catch (Exception e) {
        }
        return defautColor;
    }

    private static Bitmap getBitmapFromImageView(ImageView imageView) {

        try {
            return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } catch (ClassCastException e) {
            return ((RoundedDrawable) imageView.getDrawable()).getSourceBitmap();
        }
    }

    private static Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }

    private static int getMutedColorFromPalette(Palette palette, int defaultColor) {
        int color = palette.getDarkVibrantColor(defaultColor);
        if (color == defaultColor) {
            color = palette.getDominantColor(defaultColor);
        }
        return color;

    }


}
