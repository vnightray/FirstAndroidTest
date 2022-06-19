package com.example.myfirstapplication.activity;

import androidx.fragment.app.Fragment;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.fragment.BookListFragment;

public class BookFragmentActivity extends SingleFragmentActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment createFragment() {
        return new BookListFragment();
    }
}
