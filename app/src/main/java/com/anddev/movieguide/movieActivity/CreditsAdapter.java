package com.anddev.movieguide.movieActivity;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.actorActivity.ActorActivity_;
import com.anddev.movieguide.model.Credits;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;

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

        if (credits.getCharacter() != null) {
            holder.description.setText("" + credits.getCharacter());
        }
        if (credits.getName() != null) {
            holder.description.setText(holder.description.getText() + "\n(" + credits.getName() + ")");
        }
        ImageTools.getImageFromInternet(context, ImageTools.IMAGE_PATH_500px + credits.getProfile_path(), holder.imageView, ImageTools.DRAWABLE_PERSON);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActorActivity_.class);
                intent.putExtra("Id", credits.getId());

                try {
                    intent.putExtra("color", PaletteTools.getColorFromImageButton(holder.imageView, 0));
                } catch (Exception e) {
                }

                context.startActivity(intent);
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
        return creditstList.size();
    }


    class CreditsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView description;

        public CreditsViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.row_known_for_imageView);
            description = itemView.findViewById(R.id.row_known_for_description);

        }
    }
}

