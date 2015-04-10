package com.example.mike.whats4lunch;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class UpdateAdapter extends IntentService {

    private static final String TAG = "com.example.mike";

    public UpdateAdapter() {
        super("UpdateAdapter");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.i(TAG, "The service has started");
    }
}