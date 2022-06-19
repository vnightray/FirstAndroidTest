package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyServiceTestActivity extends AppCompatActivity {

    private Button startbtn;
    private Button stopbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myservice_test1);
        startbtn = findViewById(R.id.servicebtn1);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(MyServiceTestActivity.this, MyServiceTest1.class);
                startService(serviceIntent);
            }
        });
        stopbtn = findViewById(R.id.servicebtn2);
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(MyServiceTestActivity.this, MyServiceTest1.class);
                stopService(serviceIntent);
            }
        });

    }




}
















