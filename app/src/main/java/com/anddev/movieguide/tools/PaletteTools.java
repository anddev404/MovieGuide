package com.anddev.movieguide.tools;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.widget.ImageButton;
import android.widget.TextView;

public class PaletteTools {

    public static void setBackgroundColorToTextViewFromProminentColorOfImageButton(TextView textView, ImageButton imageButton, int defautColor) {
        try {
            textView.setBackgroundColor(getMutedColorFromPalette(createPaletteSync(getBitmapFromImageButton(imageButton)), defautColor));
        } catch (Exception e) {

        }
    }

    private static Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }

    private static Bitmap getBitmapFromImageButton(ImageButton imageButton) {
        return ((BitmapDrawable) imageButton.getDrawable()).getBitmap();

    }

    private static int getMutedColorFromPalette(Palette palette, int defaultColor) {
        return palette.getMutedColor(defaultColor);

    }


}
