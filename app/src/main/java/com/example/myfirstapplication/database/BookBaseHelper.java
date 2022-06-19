package com.example.myfirstapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myfirstapplication.database.BookDbSchema.BookTable;

/**
 * 主要负责数据库的创建、打开、更新等。
 */
public class BookBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "bookBase.db";

    public BookBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BookTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                BookTable.Cols.UUID + ", " +
                BookTable.Cols.NAME + ", " +
                BookTable.Cols.DATE + ", " +
                BookTable.Cols.READ + ", " +
                BookTable.Cols.OWNER +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
