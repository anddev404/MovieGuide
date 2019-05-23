package com.anddev.movieguide;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class KnownForListViewAdapter extends BaseAdapter {
    List<Cast> cast;
    Activity context;
    private static LayoutInflater inflater = null;

    public KnownForListViewAdapter(Activity mainActivity, List<Cast> cast) {
        // TODO Auto-generated constructor stub
        context = mainActivity;
        this.cast = cast;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cast.size();
    }

    @Override
    public Object getItem(int position) {//tu dopisac pobranie obiektu z listy
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {//dopisać
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.row_known_for, null);
        holder.imageView = (ImageView) rowView.findViewById(R.id.row_known_for_imageView);
        holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + cast.get(position).getOriginal_title(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}

