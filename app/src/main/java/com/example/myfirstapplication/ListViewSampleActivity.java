package com.example.myfirstapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ListViewSampleActivity extends AppCompatActivity {
    static final String TAG = "ListViewSample";
    private String[] mStrings = {"北京市", "天津市", "上海市", "重庆市", "乌鲁木齐"};
    private String[] icons = {"Beijing", "Tianjin", "Shanghai", "Chongqing", "Wulumuqi"};
    public Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewsample);
        init();
    }

    protected void init(){
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings);
        MyAdaptor myAdaptor = new MyAdaptor(this, R.layout.listview_item, mStrings, icons);
        ListView listView = findViewById(R.id.ListView01);
        listView.setAdapter(myAdaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "选择： " + mStrings[i]);
            }
        });


        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListViewSampleActivity.this, "wahahaha, error!!", Toast.LENGTH_LONG).show();
            }
        });
    }



}











