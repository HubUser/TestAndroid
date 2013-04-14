package com.example.TestAndroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RingToneAdapter extends ArrayAdapter<RingTone> {
    private final Context context;
    private ArrayList<RingTone> ringTones;

    public RingToneAdapter(Context context, ArrayList<RingTone> urls) {
        super(context, R.layout.itemring, urls);
        this.context = context;
        this.ringTones = urls;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.itemring, null, true);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.name);
            holder.desc= (TextView) rowView.findViewById(R.id.desc);
            rowView.setTag(holder);

        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.name.setText(ringTones.get(position).ringName);
        holder.desc.setText(ringTones.get(position).ringDec);

        return rowView;
    }

    static class ViewHolder {
        public TextView name;
        public TextView desc;
    }

}