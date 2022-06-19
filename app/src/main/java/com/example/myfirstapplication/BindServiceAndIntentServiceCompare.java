package com.example.myfirstapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BindServiceAndIntentServiceCompare extends AppCompatActivity {
    private MyServiceTest1 myServiceTest1;
    private MyIntentServiceTest myIntentServiceTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bindservice_and_intentservice_compare);
        Button bindService = findViewById(R.id.BindServicebtn);

    }
}
