package com.example.myfirstapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.GregorianCalendar;

public class DownloadSystemReceiver extends BroadcastReceiver {

    private static String TAG = "DownloadSystemReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "SystemBootReceiver...");
        Intent it = new Intent(context, DownloadService.class);
        context.startService(it);
    }
}
