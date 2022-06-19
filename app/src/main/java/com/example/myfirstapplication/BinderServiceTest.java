package com.example.myfirstapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class BinderServiceTest extends Service {
    private static String TAG = "BinderServiceTest";
    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder{
        BinderServiceTest getService(){
            return BinderServiceTest.this;
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "调用onCreate...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "调用onBind...");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "调用onUnbind...");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "调用onDestroy...");
    }

    public Date getDate(){
        Date date = new Date();
        return date;
    }

}
