package com.example.myfirstapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.fragment.BookItemDetailFragment;
import com.example.myfirstapplication.model.BookItem;
import com.example.myfirstapplication.model.BookList;

import java.util.List;
import java.util.UUID;

/**
 * 可以用来实现打开图片后的左右移动 浏览上下图片。
 */
public class BookPagerActivity extends AppCompatActivity {

    private static final String EXTRA_BOOK_ID = "com.example.myfirstapplication.activity.book_id";

    private ViewPager mViewPager;

    private List<BookItem> mBookItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_pager);

        UUID uuid = (UUID) getIntent()
                .getSerializableExtra(EXTRA_BOOK_ID);

        mViewPager = findViewById(R.id.activity_book_pager_view_pager);

        mBookItems = BookList.getInstance(this).getBookItems();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                BookItem item = mBookItems.get(position);
                return BookItemDetailFragment.newInstance(item.getUUID());
            }

            @Override
            public int getCount() {
                return mBookItems.size();
            }
        });

        for (int i = 0; i < mBookItems.size(); i++) {
            if (mBookItems.get(i).getUUID().equals(uuid)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


    public static Intent newIntent(Context context, UUID uuid){
        Intent intent = new Intent(context, BookPagerActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, uuid);
        return intent;
    }
}