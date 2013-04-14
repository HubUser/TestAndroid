package com.example.TestAndroid;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.nostra13.universalimageloader.imageloader.DisplayImageOptions;
import com.nostra13.universalimageloader.imageloader.ImageLoader;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: veal
 * Date: 4/14/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class AvatarDialog extends Dialog {

    private Context _context;
    private DisplayImageOptions options;
    ArrayList<String> urls;

    public AvatarDialog(Context context) {
        super(context);
        this._context = context;
        setContentView(R.layout.avatardialog);
        ListView avatarList = (ListView) findViewById(R.id.avatarlist);
        urls = new ArrayList<String>();
        for (int i = 1; i < 13; i++) {
            if (i < 9)
            urls.add("http://onoapps.com/Dev/" +
                    "avatars/a0" + i + ".png");
            else
                urls.add("http://onoapps.com/Dev/" +
                        "avatars/a" + i + ".png");
        }
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.stub_image)
                .cacheInMemory()
                .cacheOnDisc()
                .build();
        avatarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ImageLoader.getInstance().displayImage(urls.get(i), ((MyActivity) _context).getAvatarView(), options, null);
                ((MyActivity) _context).onAvatarSelected(urls.get(i));
                dismiss();
            }
        });

        avatarList.setAdapter(new AvatarAdapter(context, urls, ImageLoader.getInstance()));
    }
}
