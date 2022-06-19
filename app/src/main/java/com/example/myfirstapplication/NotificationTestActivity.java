package com.example.myfirstapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationTestActivity extends AppCompatActivity {

    private static String TAG = "NotificationTestActivity";

    private static final int NOTIFY_ME_ID = 12345;

    // Timer是一个定时器
    private Timer timer = new Timer();
    // 通知管理器
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twobutton);

        Button firstbtn = findViewById(R.id.firstbtn);
        firstbtn.setText("firstbtn");
        firstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                    }
                };
                // 延时3秒发送
                timer.schedule(task, 3000);

            }
        });

        Button lastbtn = findViewById(R.id.lastbtn);
        lastbtn.setText("lastbtn");
        lastbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 取消显示在通知列表中的指定通知
                mNotificationManager.cancel(NOTIFY_ME_ID);
            }
        });

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void notifyMe(){
        // 设置单击通知后所打开的详细界面
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, NotificationTestActivity.class), 0);

        Resources res = this.getResources();

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setContentTitle("通知发送人")
                .setContentText("我是详细的通知")
                .setContentIntent(pendingIntent).build();

        // 发送通知
        mNotificationManager.notify(NOTIFY_ME_ID, notification);

    }
}








