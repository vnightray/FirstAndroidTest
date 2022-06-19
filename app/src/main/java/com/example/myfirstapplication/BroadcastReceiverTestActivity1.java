package com.example.myfirstapplication;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BroadcastReceiverTestActivity1 extends AppCompatActivity {

    private static String TAG = "BroadcastReceiverTestActivity1";
    private static String ACTION_APP_BROADCAST = "com.example.myfirstapplication.MyBroadcastReceiver1";
    private static String ACTION_APP_INNER_BROADCAST = "com.example.myfirstapplication.MyInnerBroadcastReceiver1";

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "收到内部广播...", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twobutton);
        Button firstbtn = findViewById(R.id.firstbtn);
        firstbtn.setText("发送外部广播");

        Button lastbtn = findViewById(R.id.lastbtn);
        lastbtn.setText("发送内部广播");

        firstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(ACTION_APP_BROADCAST);
                sendBroadcast(intent);
            }
        });

        lastbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(ACTION_APP_INNER_BROADCAST);
                sendBroadcast(intent);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_APP_INNER_BROADCAST);
        registerReceiver(mBroadcastReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}














