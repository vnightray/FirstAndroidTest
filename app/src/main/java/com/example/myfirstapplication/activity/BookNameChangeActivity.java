package com.example.myfirstapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.model.BookItem;

import java.util.UUID;

public class BookNameChangeActivity extends AppCompatActivity {

    private static String TAG = "BookNameChangeActivity";

    private EditText mEditText;

    private Button mButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookname_input_item);
        mEditText = findViewById(R.id.editText);
        mButton = findViewById(R.id.confirm_button);
        Intent intent = getIntent();
        // 注意！： 经过intent传输的Serializable对象，和传输前的对象并非同一个，经过hashCode比对，发现并不相同。
        // 因此，若传输后需要对原来对象进行更改，需要在onResume() 方法中处理，同时 adapter.notifyDataSetChanged
        BookItem bookItem = (BookItem) intent.getSerializableExtra("bookItem");
        if (bookItem != null){
            mEditText.setText(bookItem.getName());
        }
        else {
            Log.i(TAG, "bookitem is null");
        }
        mButton.setOnClickListener(v -> {
            bookItem.setName(mEditText.getText().toString());
            Intent backIntent = new Intent();
            backIntent.putExtra("bookItem", bookItem);
            setResult(1, backIntent);
            finish();
        });

    }

}
