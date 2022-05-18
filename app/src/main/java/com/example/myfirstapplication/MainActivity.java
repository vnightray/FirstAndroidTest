package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public Button btn;
    public TextView tv;
    public boolean flag = false;

    private TextView tview1;
    private TextView tview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        tv = findViewById(R.id.textView);
        if (flag){
            tv.setText("flag is true!");
        }
        else {
            tv.setText("flag is false!");
        }
        flag = !flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tview1 = findViewById(R.id.tview1);
        tview2 = findViewById(R.id.tview2);
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                tview1.setText("手指抬起");
                break;
            case MotionEvent.ACTION_DOWN:
                tview1.setText("手指按下");
                break;
            case MotionEvent.ACTION_MOVE:
                tview1.setText("手指移动");
                break;
        }

        float locationX = event.getX();
        float locationY = event.getY();
        tview2.setText("位置为： ( " + locationX + " , " + locationY + " )");
        return true;
    }













}