package com.example.myfirstapplication.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.myfirstapplication.model.BookItem;

import java.util.Date;
import java.util.UUID;

public class BookCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public BookCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public BookItem getBook(){
        String uuidString = getString(getColumnIndex(BookDbSchema.BookTable.Cols.UUID));
        String name = getString(getColumnIndex(BookDbSchema.BookTable.Cols.NAME));
        Long date = getLong(getColumnIndex(BookDbSchema.BookTable.Cols.DATE));
        int isRead = getInt(getColumnIndex(BookDbSchema.BookTable.Cols.READ));

        BookItem book = new BookItem(UUID.fromString(uuidString));
        book.setName(name);
        book.setCreateDate(new Date(date));
        book.setRead(isRead != 0);

        return book;
    }
}
