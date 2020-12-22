package com.example.bigproject;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Pictures> data;
    private LayoutInflater layoutInflater;

    public MyAdapter(Activity activity, ArrayList<Pictures> data) {
        this.activity = activity;
        this.data=data;
        this.layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view=layoutInflater.inflate(R.layout.image_file,null);
        }
        ImageView img=view.findViewById(R.id.image_box);
        img.setImageBitmap(data.get(position).getBm());
        TextView txtName=view.findViewById(R.id.image_name);
        txtName.setText(data.get(position).getName());
        TextView txtDate=view.findViewById(R.id.image_date);
        txtDate.setText(data.get(position).getDateTime());

        return view;
    }

}
