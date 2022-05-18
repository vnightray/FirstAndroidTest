package com.example.myfirstapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class RunnableSample extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "RunnableSample";
    private ToggleButton toggleButton;
    private TextView textView;
    private boolean flag = true;
//    public volatile boolean interupt = true;
    private Thread mClockThread;
    private Runnable runnable;
    private int count = 0;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runnablesample);
        toggleButton = findViewById(R.id.toggleButton);
        textView = findViewById(R.id.textView2);
        toggleButton.setTextOff("计时停止");
        toggleButton.setOnClickListener(this);

        runnable = new Runnable() {
            @Override
            public void run() {
                count = 0;
                while (true) {
                    try {
                        Thread.currentThread().sleep(1000);
//                        Log.i(TAG, "interrupted : " + Thread.currentThread().isInterrupted());
//                        if (Thread.currentThread().isInterrupted()){
//                            break;
//                        }
                        // 子线程无法直接修改主线程创建的ui对象, 需要通过message来传递消息
//                    textView.setText(String.valueOf(++count));
                        count++;
                        Message msg = new Message();
                        msg.obj = count;
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                        Log.i(TAG, "逝去了 " + count + " 秒 ");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
//            if (Thread.currentThread().isInterrupted()){
//                Thread.currentThread().stop();
//            }
            }

        };

//        mClockThread.start();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        textView.setText("逝去了 " + msg.obj + " 秒 ");
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggleButton:
                if (flag) {
//                    Log.i(TAG, "mClockThread is " + (mClockThread.isAlive() ? "alive" : "dead"));
//                    interupt = true;
                    mClockThread = new Thread(runnable);
                    mClockThread.start();
                    flag = false;
                    Log.i(TAG, "线程启动");
                } else {
//                    interupt = false;
                    mClockThread.interrupt();
                    flag = true;
                    Log.i(TAG, "我点击了");
                }
                break;
        }
    }


}
