package com.anddev.movieguide.moviesActivity;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anddev.movieguide.R;

public class CustomImageView extends LinearLayout {

    private ImageButton image;
    private TextView textView;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        try {

            inflate(context, R.layout.custom_view_image_with_text, this);

            image = findViewById(R.id.custom_view_imageButton);
            textView = findViewById(R.id.custom_view_text_view);

            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
            image.setImageDrawable(attributes.getDrawable(R.styleable.CustomImageView_image));
            textView.setText(attributes.getString(R.styleable.CustomImageView_text));
            attributes.recycle();

        } catch (Exception e) {

        }
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageButton getImageButton() {
        return image;
    }

    public TextView getTextView() {
        return textView;
    }


    @Override
    public boolean performClick() {
        // Calls the super implementation, which generates an AccessibilityEvent
        // and calls the onClick() listener on the view, if any
        super.performClick();

        // Handle the action for the custom click here

        return true;
    }

}
