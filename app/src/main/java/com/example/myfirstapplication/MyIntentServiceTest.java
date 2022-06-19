package com.example.myfirstapplication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyIntentServiceTest extends IntentService {

    private static String TAG = "MyIntentServiceTest";
    public MyIntentServiceTest(){
        super("MyIntentServiceTest");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "调用onHandleIntent...");
        synchronized (this){
            try {
                wait(5 * 1000);
                stopSelf();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "调用onDestroy...");
    }
}
