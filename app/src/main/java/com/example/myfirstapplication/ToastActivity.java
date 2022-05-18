package com.example.myfirstapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ToastActivity extends AppCompatActivity {
    public Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toast_sample);
        init();
    }

    protected void init(){
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建imageview对象
                ImageView imageView = new ImageView(ToastActivity.this);
                imageView.setImageResource(R.mipmap.ic_launcher);
                // 创建textview对象
                TextView textView = new TextView(ToastActivity.this);
                textView.setText("error happened !!!");

                // 创建线性布局对象
                LinearLayout layout = new LinearLayout(ToastActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(imageView);
                layout.addView(textView);

                // 创建Toast对象
                Toast toast = new Toast(ToastActivity.this);
                toast.setView(layout);
                toast.show();


            }
        });
    }
}
