package com.example.TestAndroid;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.imageloader.DisplayImageOptions;
import com.nostra13.universalimageloader.imageloader.ImageLoader;
import com.nostra13.universalimageloader.imageloader.ImageLoadingListener;

import java.util.ArrayList;

public class AvatarAdapter extends ArrayAdapter<String> {
    private final Context context;
    private ArrayList<String> urls;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public AvatarAdapter(Context context, ArrayList<String> urls, ImageLoader imageLoader) {
        super(context, R.layout.item, urls);
        this.context = context;
        this.urls = urls;
        this.imageLoader = imageLoader;

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.stub_image)
                .cacheInMemory()
                .cacheOnDisc()
                .build();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.item, null, true);
            holder = new ViewHolder();
            holder.avatar = (ImageView) rowView.findViewById(R.id.avatar);
            holder.name = (TextView) rowView.findViewById(R.id.avatar_name);

            holder.background  = (RelativeLayout) rowView.findViewById(R.id.avatar_bg);

            rowView.setTag(holder);

        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        if (position % 2 == 0)
            holder.background.setBackgroundResource(R.drawable.viewer);
        else
            holder.background.setBackgroundResource(R.drawable.tab_unselected);
        holder.name.setText("Avatar #" + position);

        imageLoader.displayImage(urls.get(position), holder.avatar, options, null);

        return rowView;
    }

    static class ViewHolder {
        public ImageView avatar;
        public TextView name;
        public RelativeLayout background;
    }

}