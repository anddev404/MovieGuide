package com.anddev.movieguide.movieActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddev.movieguide.R;
import com.anddev.movieguide.actorActivity.ActorActivity_;
import com.anddev.movieguide.databinding.RowKnownForBinding;
import com.anddev.movieguide.model.Cast;
import com.anddev.movieguide.model.Credits;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.PaletteTools;

import java.util.ArrayList;
import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CreditsViewHolder> {

    private Activity context;
    private List<CreditItemViewModel> listViewModel;

    private List<Credits.Cast> creditstList;

    public CreditsAdapter(Activity context, List<Credits.Cast> creditstList) {
        this.context = context;
        this.creditstList = creditstList;


        listViewModel = new ArrayList<>();
        for (Credits.Cast c : creditstList) {
            listViewModel.add(new CreditItemViewModel(c.getName() + " - " + c.getId()));

        }


    }

    RowKnownForBinding binding;

    @Override
    public CreditsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        // View view = inflater.inflate(R.layout.row_known_for, parent, false);
        binding = RowKnownForBinding.inflate(inflater);// RowKnownForBinding to generowana klasa na podstawie nazwy layoutu
//    return new CreditsViewHolder(binding);//przesłanie dla holdera wygenerowanej klasy bindującej a nie view-a
        return new CreditsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CreditsViewHolder holder, int position) {

        Credits.Cast credits = creditstList.get(position);

        holder.binduj(listViewModel.get(position));

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

    }


    @Override
    public int getItemCount() {
        return creditstList.size();
    }


    class CreditsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public CreditsViewHolder(RowKnownForBinding itemView) {
            super(itemView.getRoot());

            imageView = itemView.getRoot().findViewById(R.id.row_known_for_imageView);

        }

        void binduj(CreditItemViewModel ccc) {
            binding.setItem(ccc);//generowana metoda z xmla
            binding.executePendingBindings();//
        }
    }
}

