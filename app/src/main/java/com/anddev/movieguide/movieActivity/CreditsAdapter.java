package com.anddev.movieguide.movieActivity;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Cast;
import com.anddev.movieguide.model.Credits;
import com.anddev.movieguide.tools.ImageTools;

import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CreditsViewHolder> {

    private Activity context;

    private List<Credits.Cast> creditstList;

    public CreditsAdapter(Activity context, List<Credits.Cast> creditstList) {
        this.context = context;
        this.creditstList = creditstList;
    }

    @Override
    public CreditsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_known_for, parent, false);

        return new CreditsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreditsViewHolder holder, int position) {

        Credits.Cast credits = creditstList.get(position);

        ImageTools.getImageFromInternet(context, ImageTools.IMAGE_PATH_500px + credits.getProfile_path(), holder.imageView, ImageTools.DRAWABLE_PERSON);


    }


    @Override
    public int getItemCount() {
        return creditstList.size();
    }


    class CreditsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public CreditsViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.row_known_for_imageView);

        }
    }
}

