package com.anddev.movieguide.actorActivity;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Profiles;
import com.anddev.movieguide.tools.ImageTools;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private ImageAdapter adapter;


    private Activity context;

    private List<Profiles> profiles;

    public ImageAdapter(Activity context, List<Profiles> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_full_screen_image, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        Profiles profil = profiles.get(position);
        ImageTools.getImageFromInternet(context, "https://image.tmdb.org/t/p/w500/" + profil.getFile_path(), holder.imageView, ImageTools.DRAWABLE_FILM);

    }


    @Override
    public int getItemCount() {
        return profiles.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.row_full_screen_image_imageView);

        }
    }
}

