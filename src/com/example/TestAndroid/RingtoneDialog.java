package com.example.TestAndroid;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.nostra13.universalimageloader.imageloader.ImageLoader;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: veal
 * Date: 4/14/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class RingtoneDialog extends Dialog {
    private Context _context;
    ArrayList<RingTone> _rings;
    public RingtoneDialog(Context context, ArrayList<RingTone> rings) {
        super(context);
        _context = context;
        _rings = rings;
        setContentView(R.layout.avatardialog);
        ListView ringtoneList = (ListView) findViewById(R.id.avatarlist);
        ringtoneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MyActivity) _context).onRingtoneSelected(_rings.get(i).ringName);
                dismiss();
            }
        });

        ringtoneList.setAdapter(new RingToneAdapter(context, rings));
    }
}
