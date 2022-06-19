package com.example.myfirstapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myfirstapplication.database.BookBaseHelper;
import com.example.myfirstapplication.database.BookCursorWrapper;
import com.example.myfirstapplication.database.BookDbSchema;
import com.example.myfirstapplication.database.BookDbSchema.BookTable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BookList implements Serializable {

    private static BookList INSTANCE;

//    private List<BookItem> mBookItems;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private BookList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BookBaseHelper(mContext).getWritableDatabase();
//        mBookItems = new ArrayList<>();
//        for (int i = 0; i < 60; i++) {
//            mBookItems.add(new BookItem("book-" + i));
//        }
    }

    public static BookList getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new BookList(context);
        }
        return INSTANCE;
    }

    public List<BookItem> getBookItems() {
//        return mBookItems;
        List<BookItem> bookItems = new ArrayList<>();

        BookCursorWrapper cursorWrapper = queryBooks(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                bookItems.add(cursorWrapper.getBook());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return bookItems;
    }

    public BookItem getItemByUuid(UUID uuid){
//        for (BookItem bookItem : mBookItems) {
//            if (bookItem.getUUID().equals(uuid)){
//                return bookItem;
//            }
//        }

        BookCursorWrapper cursorWrapper = queryBooks(BookTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()});

        try {
            if (cursorWrapper.getCount() == 0){
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getBook();
        } finally {
            cursorWrapper.close();
        }

    }

    public boolean addBook(BookItem item){
        if (null == item){
            return false;
        }
//        mBookItems.add(item);
        ContentValues values = getContentValues(item);

        mDatabase.insert(BookTable.NAME, null, values);
        return true;
    }


    public void updateBook(BookItem item){
        String uuidString = item.getUUID().toString();
        ContentValues values = getContentValues(item);
        mDatabase.update(BookTable.NAME, values,
                BookTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public boolean deleteBook(BookItem item){
        if (null == item){
            return false;
        }
        String uuidString = item.getUUID().toString();
        ContentValues values = getContentValues(item);
        int delete = mDatabase.delete(BookTable.NAME, BookTable.Cols.UUID + " = ?", new String[]{uuidString});
        if (delete != -1){
            return true;
        }
        return false;
    }

//    private Cursor queryBooks(String whereClause, String[] whereArgs){
//        Cursor cursor = mDatabase.query(
//                BookTable.NAME,
//                null, // columns - null selects all columns
//                whereClause,
//                whereArgs,
//                null, null, null
//        );
//
//        return cursor;
//    }

    private BookCursorWrapper queryBooks(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                BookTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new BookCursorWrapper(cursor);
    }




    private static ContentValues getContentValues(BookItem book){
        ContentValues values = new ContentValues();
        values.put(BookTable.Cols.UUID, book.getUUID().toString());
        values.put(BookTable.Cols.NAME, book.getName());
        values.put(BookTable.Cols.DATE, book.getCreateDate().getTime());
        values.put(BookTable.Cols.READ, book.isRead() ? 1 : 0);

        return values;
    }

    public File getPhotoFile(BookItem item){
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, item.getPhotoFilename());
    }


}
