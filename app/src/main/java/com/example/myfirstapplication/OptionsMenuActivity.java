package com.example.myfirstapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsMenuActivity extends AppCompatActivity {
    public static String TAG = "fontvar";
    private TextView mTextView;
    public static final int RED_MENU_ID = Menu.FIRST;
    public static final int GREEN_MENU_ID = Menu.FIRST + 1;
    public static final int BLUE_MENU_ID = Menu.FIRST + 2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_menu);
        mTextView = findViewById(R.id.tview1);
        mTextView.setText(R.string.modify);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0, RED_MENU_ID, 0, R.string.grater)
                .setIcon(R.mipmap.ic_launcher)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, GREEN_MENU_ID, 0, R.string.smaller)
                .setIcon(R.mipmap.ic_launcher)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, BLUE_MENU_ID, 0, "others")
                .setIcon(R.mipmap.ic_launcher)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case RED_MENU_ID:
                mTextView.setBackgroundColor(Color.RED);
                mTextView.setText(R.string.grater);
                mTextView.setTextSize(mTextView.getTextSize() * 1.2f);
                Log.i(TAG, String.valueOf(mTextView.getTextSize()));
                return true;
            case GREEN_MENU_ID:
                mTextView.setBackgroundColor(Color.GREEN);
                mTextView.setText(R.string.smaller);
                mTextView.setTextSize(mTextView.getTextSize() * 0.2f);
                Log.i(TAG, String.valueOf(mTextView.getTextSize()));
                return true;
            case BLUE_MENU_ID:
                mTextView.setBackgroundColor(Color.BLUE);
                mTextView.setText("其他others");
                Log.i(TAG, String.valueOf(mTextView.getTextSize()));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
