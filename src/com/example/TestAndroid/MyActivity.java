package com.example.TestAndroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ArrayList<RingTone> ringTones = new ArrayList<RingTone>();
    private ImageView avatarView;
    public String currentAvatar;
    public String currentRingtone;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        avatarView = (ImageView) findViewById(R.id.avatarview);
        Button chooseAvatar = (Button) findViewById(R.id.choose_avatar);
        final EditText nameEdit = (EditText) findViewById(R.id.user_name);
        final EditText phoneEdit = (EditText) findViewById(R.id.user_phone);
        chooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog avatarPicker = new AvatarDialog(MyActivity.this);
                avatarPicker.show();
            }
        });

        Button chooseRingtone = (Button) findViewById(R.id.choose_ringtone);
        chooseRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog ringtonePicker = new RingtoneDialog(MyActivity.this, ringTones);
                ringtonePicker.show();
            }
        });

        new RingTask().execute();
        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                String ringtone = currentRingtone;
                String avatar= currentAvatar;

                StringBuilder builder = new StringBuilder();
                builder.append("name: ").append(name).append("\n");
                builder.append("phone: ").append(phone).append("\n");
                builder.append("ringtone: ").append(ringtone).append("\n");
                builder.append("avatar: ").append(avatar).append("\n");

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, "welcome@onoapps.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, builder.toString());

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }

    public void onRingtoneSelected(String ringtone) {
        currentRingtone = ringtone;
    }

    public void onAvatarSelected(String avatar) {
        currentAvatar = avatar;
    }

    public ImageView getAvatarView() {
        return avatarView;
    }

    private void update() {
        InputStream source = retrieveStream("http://onoapps.com/Dev/ringtones.txt");
        if (source == null)
            return;

        BufferedReader reader = new BufferedReader(new InputStreamReader(source));
        StringBuilder builder = new StringBuilder();
        try {
            for (String line; (line = reader.readLine()) != null; ) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        parse(builder.toString());
    }

    public void parse(String in) {
        JSONTokener jsonTokener = new JSONTokener(in);
        JSONObject finalJson;
        try {
            finalJson = new JSONObject(jsonTokener);
            JSONArray children = finalJson.getJSONArray("ringtones");
            for (int i = 0, end = children.length(); i < end; i++) {
                JSONObject twittJson = children.getJSONObject(i); //returns the ith JSONObject containing id, name, data, etc.
                RingTone ringTone = new RingTone(twittJson);
                ringTones.add(ringTone);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class RingTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(String... strings) {
            update();
            return 1;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
        }

    }

    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);
        try {

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(),
                        "Error " + statusCode + " for URL " + url);
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();

        } catch (IOException e) {
            getRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }
        return null;
    }
}
