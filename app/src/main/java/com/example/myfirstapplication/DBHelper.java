package com.example.myfirstapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static String TAG = "DBHelper";

    public DBHelper(Context context){
        super(context, "Exercise", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            StringBuffer sql = new StringBuffer();
            sql.append("CREATE TABLE ");
            sql.append("Exercise");
            sql.append(" (field_date Text, field_input Text, field_output Text," +
                    " field_weight Text, field_amountexercise Text);");
            Log.i(TAG, sql.toString());
            sqLiteDatabase.execSQL(sql.toString());
            sqLiteDatabase.execSQL("insert into Exercise (field_date,field_input,field_output)" +
                    " values('2016-10-8 17:24:27', '1300大卡', '3300大卡')");

            sqLiteDatabase.execSQL("insert into Exercise (field_date,field_input,field_output)" +
                    " values('2017-4-23 14:52:25', '1600大卡', '4500大卡')");

        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF　EXISTS " + "exercise");
        onCreate(sqLiteDatabase);
    }
}

















