package com.anddev.movieguide;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class KnownForAdapter extends RecyclerView.Adapter<KnownForAdapter.CastViewHolder> {
    private KnownForAdapter adapter;


    private Activity context;

    private List<Cast> castList;

    public KnownForAdapter(Activity context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_known_for, parent, false);

        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {

        Cast cast = castList.get(position);

        ImageTools.getImageFromInternet(context, "https://image.tmdb.org/t/p/w500/" + cast.getPoster_path(), holder.imageView, ImageTools.DRAWABLE_FILM);


    }


    @Override
    public int getItemCount() {
        return castList.size();
    }


    class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public CastViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.row_known_for_imageView);

        }
    }
}

