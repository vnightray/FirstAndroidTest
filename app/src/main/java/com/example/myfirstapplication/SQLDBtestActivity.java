package com.example.myfirstapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SQLDBtestActivity extends AppCompatActivity implements View.OnClickListener{
    public static String TAG = "SQLDBtestActivity";
    private DBHelper mDBHelper;
    private Button btn_insert;
    private Button btn_delete;
    private Button btn_update;
    private Button btn_search;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbsearch_test);
        mDBHelper = new DBHelper(this);
        btn_insert = findViewById(R.id.databaseInsert);
        btn_insert.setOnClickListener(this);
        btn_delete = findViewById(R.id.databaseDelete);
        btn_delete.setOnClickListener(this);
        btn_update = findViewById(R.id.databaseUpdate);
        btn_update.setOnClickListener(this);
        btn_search = findViewById(R.id.databaseSearch);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.databaseInsert:
                dbinsert();
                break;
            case R.id.databaseDelete:
                dbDelete();
                break;
            case R.id.databaseUpdate:
                dbUpdate();
                break;
            case R.id.databaseSearch:
                dbSearch();
                break;
        }
    }

    public void dbinsert(){
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("field_date", "2020-11-12 22:22:22");
        values.put("field_input", "1300大卡");
        values.put("field_output", "3300大卡");
        values.put("field_weight", "88kg");
        values.put("field_amountexercise", "5公里");

        long rowid = database.insert("Exercise", null, values);
        Log.i(TAG, "受影响行数: " + rowid);
    }

    public void dbDelete(){
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        String whereClause = "field_input = ?";
        long rowid = database.delete("Exercise", whereClause, new String[]{"1300大卡"});
        Log.i(TAG, "受影响行数: " + rowid);
    }

    public void dbUpdate(){
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("field_date", "2019-8-8 17:17:17");
        values.put("field_input", "1700大卡");
        values.put("field_output", "3700大卡");
        values.put("field_weight", "87kg");
        values.put("field_amountexercise", "7公里");

        String whereClause = "field_input = ?";
        long rowid = database.update("Exercise", values, whereClause, new String[]{"1300大卡"});
        Log.i(TAG, "受影响行数: " + rowid);
    }

    public void dbSearch(){
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        String[] columns = new String[]{"field_date", "field_input", "field_output", "field_weight", "field_amountexercise"};
        Cursor mCursor = database.query("Exercise", columns, null, null, null, null, "field_weight" + " asc");

        while (mCursor.moveToNext()){
            Log.i(TAG, "field_input  --  " + mCursor.getString(mCursor.getColumnIndex("field_input")));
//            Log.i(TAG, mCursor.getString(mCursor.getColumnIndex("field_output")));
        }

        startManagingCursor(mCursor);
    }

}












