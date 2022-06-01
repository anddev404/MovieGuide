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

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.ProfilesViewHolder> {
    private ProfilesAdapter adapter;


    private Activity context;

    private List<Profiles> profilesList;


    public ProfilesAdapter(Activity context, List<Profiles> profilesList) {
        this.context = context;
        this.profilesList = profilesList;
    }

    @Override
    public ProfilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_known_for, parent, false);

        return new ProfilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfilesViewHolder holder, int position) {

        Profiles image = profilesList.get(position);

        ImageTools.getImageFromInternet(context, "https://image.tmdb.org/t/p/w500/" + image.getFile_path(), holder.imageView, ImageTools.DRAWABLE_PERSON);


    }

    @Override
    public int getItemCount() {
        return profilesList.size();
    }


    class ProfilesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ProfilesViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.row_known_for_imageView);

        }
    }
}

