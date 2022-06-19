package com.example.myfirstapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.activity.BookNameChangeActivity;
import com.example.myfirstapplication.activity.BookPagerActivity;
import com.example.myfirstapplication.model.BookItem;
import com.example.myfirstapplication.model.BookList;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class BookListFragment extends Fragment {

    private static String TAG = "BookListFragment";

    public static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

    // 存储subtitle的状态信息，用以恢复页面， 按返回键可以正常恢复，但是使用actionbar的返回就不可以正常恢复（在翻转手机的情况下）
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mRecyclerView;

    private BookAdapter mAdapter;

    private int changedItemId = 0;

    // 用于跟踪记录进入 onCreateView 的次数
    private int count = 0;

    // 控制 菜单栏 的显示
    private boolean mSubtitleVisible;

    private TextView mBlankTextView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 第一步，告诉当前activity 有actionBar需要配置
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        Log.i(TAG, " 进入onCreateView " + count++);

        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        mRecyclerView = view.findViewById(R.id.book_recycler_view);

        // container is not null
//        Log.i(TAG, container == null ? "container is " + " null " : "container is " + " not null");

        // setLayoutManager 的作用，个人感觉在于：recyclerview本身只是一个包含许多ViewHolder的集合，
        // 并不负责 如何部署和展示 ViewHolder，需要通过设置layoutManager 来管理其 ViewHolder（s），
        // 通过设定LayoutManager 的种类和参数，可以实现个性化多样化的布局形式。
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI(container);

        return view;
    }

    private void updateUI(ViewGroup container) {
        BookList bookList = BookList.getInstance(getActivity());
        List<BookItem> bookItemList = bookList.getBookItems();

        mAdapter = new BookAdapter(bookItemList);
        mRecyclerView.setAdapter(mAdapter);

        if (bookItemList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mBlankTextView = new TextView(getActivity());
            mBlankTextView.setText("书架是空的，待添加。。。");
            container.addView(mBlankTextView);
        }



    }

    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView mUuidTextView;
        private TextView mNameTextView;
        private TextView mDateTextView;
        private BookItem mBookItem;
        private Button mDateChangeBtn;


        public BookHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_book, parent, false));
            mUuidTextView = itemView.findViewById(R.id.book_uuid);
            mNameTextView = itemView.findViewById(R.id.book_name);
            mDateTextView = itemView.findViewById(R.id.book_date);
            mDateChangeBtn = itemView.findViewById(R.id.DialogBtn);
            mDateChangeBtn.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void bind(BookItem item) {
            mBookItem = item;
            mUuidTextView.setText(mBookItem.getUUID().toString());
//            Log.i(TAG,"item + " + sSimpleDateFormat.format(mBookItem.getCreateDate()) + getAdapterPosition());


            mNameTextView.setText(mBookItem.getName());
//            Log.i(TAG, mBookItem.getName());

            mDateTextView.setText(sSimpleDateFormat.format(mBookItem.getCreateDate()));
//            Log.i(TAG, sSimpleDateFormat.format(mBookItem.getCreateDate()));
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), ((TextView)v.findViewById(R.id.book_name)).getText(),Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(), mBookItem.getName(), Toast.LENGTH_SHORT).show();

            /*
            Intent intent = new Intent();
            intent.putExtra("bookItem", mBookItem);

            // 利用 getAdapterPosition 方法获取被点击的ViewHolder 在 adapter 中的 position， 然后
            // 在 onResume方法中 调用 adapter.notifyItemChanged 方法，可以轻量化动态更新数据，而不需要 调用
            // notifyDataSetChanged 进行全部 ViewHolder 的更新
            changedItemId = getAdapterPosition();
            Log.i(TAG, "changedItemId : " + changedItemId);
            intent.setClass(getActivity(), BookNameChangeActivity.class);
            startActivityForResult(intent, 1);

             */

            Intent intent = BookPagerActivity.newIntent(getActivity(), mBookItem.getUUID());
            changedItemId = getAdapterPosition();
            startActivity(intent);
        }

        // 实现长按ViewHolder，删除该条目
//        @Override
//        public boolean onLongClick(View v) {
//
//            Log.i(TAG, "long click action");
//
//            boolean result = BookList.getInstance(getActivity()).deleteBook(mBookItem);
//            Log.i(TAG, "删除成功 " + mBookItem.getName());
////            mAdapter.notifyItemChanged(this.getAdapterPosition());
//            mAdapter.setBookList(BookList.getInstance(getActivity()).getBookItems());
//            mAdapter.notifyItemRemoved(getAdapterPosition());
//            return true;
//        }


        @Override
        public boolean onLongClick(View v) {



            return true;
        }
    }


    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {

        private List<BookItem> mBookList;

        public BookAdapter(List<BookItem> bookList) {
            mBookList = bookList;
        }

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookHolder(LayoutInflater.from(getActivity()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            BookItem item = mBookList.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }

        public void setBookList(List<BookItem> bookItems){
            mBookList = bookItems;
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        BookList bookList = BookList.getInstance(getActivity());
        List<BookItem> books = bookList.getBookItems();

        if (books.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            if (mBlankTextView != null) {
                mBlankTextView.setVisibility(View.GONE);
            }
        }

        if (mAdapter == null) {
            mAdapter = new BookAdapter(books);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setBookList(books);

            // 刷新recyclerview的所有ViewHolder
            mAdapter.notifyDataSetChanged();

            // 用以轻量化刷新单个ViewHolder
//            mAdapter.notifyItemChanged(changedItemId);
//            Log.i(TAG, "notifyItemChanged: " + changedItemId);
        }


        Log.i(TAG, "onResume subtitlevisible " + mSubtitleVisible);

        updateSubtitle();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 1) {
            BookList bookList = BookList.getInstance(getActivity());
            BookItem backItem = (BookItem) data.getSerializableExtra("bookItem");
//            if (backItem != null){
//                Log.i(TAG, "backitem is not null");
//            }
//            else {
//                Log.i(TAG, "backitem is null!!!");
//            }
            BookItem changedItem = bookList.getItemByUuid(backItem.getUUID());
            changedItem.setName(backItem.getName());
        }
    }


    private static final String ARG_BOOK_ID = "book_id";


    public static BookListFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_ID, uuid);

        BookListFragment fragment = new BookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 用于初始化 OptionMenu
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_book_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);

        Log.i(TAG, "onCreateOptionMeny msubtitlevisible: " + mSubtitleVisible);

        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_book:
                BookItem book = new BookItem(new String("wahaaha"));
                BookList.getInstance(getActivity()).addBook(book);
                Intent intent = BookPagerActivity.newIntent(getActivity(), book.getUUID());

                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateSubtitle() {
        BookList bookList = BookList.getInstance(getActivity());
        int bookCounts = bookList.getBookItems().size();
        String subtitle = getString(R.string.subtitle_format, bookCounts);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        Log.i(TAG, "updateSubtitle msubtitlevisible: " + mSubtitleVisible);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
        Log.i(TAG, "saved subtitlevisible: " + mSubtitleVisible);
    }
}
