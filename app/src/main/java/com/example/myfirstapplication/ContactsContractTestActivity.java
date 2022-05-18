package com.example.myfirstapplication;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ContactsContractTestActivity extends AppCompatActivity
        implements AdapterView.OnItemLongClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static String TAG = "ContactsContractTestActivity";
    private ListView mListView;
    private SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactscontract_test);
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.listview_item, null,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                new int[]{R.id.textview1, R.id.textview2} ,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        mListView = findViewById(R.id.ListView01);
        mListView.setAdapter(simpleCursorAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        simpleCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleCursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        {
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, l);
            String[] columns = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(uri, columns, null, null, null);
            if (cursor.moveToNext()){
                final String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                final String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i(TAG, contactId + " | " + contactName);
            }
        }
        return false;
    }
}











