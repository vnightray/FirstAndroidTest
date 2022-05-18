package com.example.myfirstapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SharedPreferenceTestActivity extends AppCompatActivity {

    private RadioGroup rdgDateFormat;
    private RadioButton rdDateFormat1, rdDateFormat2;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedpreference_config);

        // 获得读取数据所用的sharedPreference
        mSharedPreferences = getSharedPreferences("config_ini", MODE_PRIVATE);

        rdgDateFormat = findViewById(R.id.radiogroup);
        rdDateFormat1 = findViewById(R.id.radio1);
        rdDateFormat2 = findViewById(R.id.radio2);

        String dateConf = mSharedPreferences.getString("dateconf", "YYYY-MM-DD");
        setDateFormat(dateConf);

        rdgDateFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                if (i == rdDateFormat1.getId()){
                    editor.putString("dateconf", "YYYY-MM-DD");
                    setDateFormat("YYYY-MM-DD");
                }
                else {
                    editor.putString("dateconf", "YYYY/MM/DD");
                }
                editor.commit();
            }
        });

    }

    private void setDateFormat(String dateFormat){
        if ("YYYY-MM-DD".equals(dateFormat)){
            rdDateFormat1.setChecked(true);
            rdDateFormat2.setChecked(false);
        }
        else {
            rdDateFormat2.setChecked(true);
            rdDateFormat1.setChecked(false);
        }
    }
}















