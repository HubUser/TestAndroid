package com.example.TestAndroid;

import org.json.JSONException;
import org.json.JSONObject;

public class RingTone {

    public String ringDec;
    public String ringName;

    public RingTone(JSONObject twittJson) {

        try {
            ringName = twittJson.getString("ringName");
            ringDec = twittJson.getString("ringDec");

        } catch (JSONException e) {

        }
    }
}
