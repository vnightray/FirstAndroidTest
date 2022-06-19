package com.example.myfirstapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DownloadService extends IntentService {

    private static String TAG = "DownloadService";

    private boolean isRunning = false;

    public DownloadService(){
        super("DownloadService");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (isRunning){
            try {
                Thread.sleep(5000);
                if (isConnected(ConnectivityManager.TYPE_WIFI)){
                    Log.i(TAG, "Download thread start...");
                    // todo 下载任务处理，任务处理完成isRunning 设置为false 停止任务
                }
            } catch (InterruptedException e){
                continue;
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean isConnected(int type){
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        Network[] networks = manager.getAllNetworks();
        NetworkInfo networkInfo;

        for (Network network : networks) {
            networkInfo = manager.getNetworkInfo(network);
            if (networkInfo != null
            && networkInfo.getType() == type
            && networkInfo.isConnected()){
                return true;
            }
        }
        return false;
    }








}
