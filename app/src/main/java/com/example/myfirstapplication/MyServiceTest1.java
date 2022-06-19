package com.example.myfirstapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyServiceTest1 extends Service {

    private static String TAG = "MyServiceTest1";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "调用oncreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "调用 onStartCommand... startId = " + startId);
        synchronized (this){
            try {
                wait(5 * 1000);
                stopSelf();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "调用了onDestroy方法。。。");
    }
}












