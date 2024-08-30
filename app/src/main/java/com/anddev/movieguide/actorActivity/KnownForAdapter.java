package com.anddev.movieguide.actorActivity;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Cast;
import com.anddev.movieguide.movieActivity.MovieActvity_;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;
import com.anddev.movieguide.tvShow.TvShowActivity_;

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
        try {
            if (cast.getMedia_type().equalsIgnoreCase("tv") && cast.getName() != null) {
                holder.description.setText("" + cast.getName());
            } else if (cast.getTitle() != null) {
                holder.description.setText("" + cast.getTitle());
            }
        } catch (Exception e) {

        }
        ImageTools.getImageFromInternet(context, "https://image.tmdb.org/t/p/w500/" + cast.getPoster_path(), holder.imageView, ImageTools.DRAWABLE_FILM);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "";
                try {
                    type = cast.getMedia_type();

                    if (type.equalsIgnoreCase("movie")) {
                        Intent intent = new Intent(context, MovieActvity_.class);
                        intent.putExtra("Id", cast.getId());
                        try {
                            intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.imageView, 0));

                        } catch (Exception e) {
                        }
                        context.startActivity(intent);
                    } else if (type.equalsIgnoreCase("tv")) {
                        Intent intent = new Intent(context, TvShowActivity_.class);
                        intent.putExtra("Id", cast.getId());
                        try {
                            intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.imageView, 0));

                        } catch (Exception e) {
                        }
                        context.startActivity(intent);
                    }

                } catch (Exception e) {

                }
            }
        });
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageView.performClick();
            }
        });
    }


    @Override
    public int getItemCount() {
        return castList.size();
    }


    class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView description;

        public CastViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.row_known_for_imageView);
            description = itemView.findViewById(R.id.row_known_for_description);

        }
    }
}

