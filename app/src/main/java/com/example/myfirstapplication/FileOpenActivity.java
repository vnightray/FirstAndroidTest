package com.example.myfirstapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileOpenActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "FileSample";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fileopen_test);
        Button writeout = findViewById(R.id.writeout);
        writeout.setOnClickListener(this);
        Button readin = findViewById(R.id.readin);
        readin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.writeout:
                write();
                break;
            case R.id.readin:
                read();
                break;
        }
    }

    private void write(){
        FileOutputStream out = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("1289645040579,1500大卡,3000大卡,90kg,5公里");
            sb.append("\n");
            sb.append("1289732522328,2500大卡,4000大卡,95kg,5公里");
            sb.append("\n");
            out = this.openFileOutput("Exercise", MODE_PRIVATE);
            out.write(sb.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void read(){
        List<Map<String, String>> list = findAll();
        for (Map<String, String> item : list) {
            Log.i(TAG, "=======================");
            Log.i(TAG, "日期: " + item.get("field_date"));
            Log.i(TAG, "摄入热量: " + item.get("field_input"));
            Log.i(TAG, "消耗热量: " + item.get("field_output"));
            Log.i(TAG, "体重: " + item.get("field_weight"));
            Log.i(TAG, "运动情况: " + item.get("field_amountexercise"));

        }

    }

    private List<Map<String, String>> findAll(){
        FileInputStream fin = null;
        List<Map<String, String>> list = new ArrayList<>();
        try {
            StringBuffer sb = new StringBuffer();
            fin = this.openFileInput("Exercise");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin, "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null){
                String[] fields = line.split(",");
                Map<String, String> rows = new HashMap<>();
                rows.put("field_date", fields[0]);
                rows.put("field_input", fields[1]);
                rows.put("field_output", fields[2]);
                rows.put("field_weight", fields[3]);
                rows.put("field_amountexercise", fields[4]);
                list.add(rows);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (fin != null){
                try {
                    fin.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }












}
