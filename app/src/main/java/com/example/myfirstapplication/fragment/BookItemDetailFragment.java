package com.example.myfirstapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.activity.BookPagerActivity;
import com.example.myfirstapplication.model.BookItem;
import com.example.myfirstapplication.model.BookList;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BookItemDetailFragment extends Fragment {

    private static String TAG = "BookItemDetailFragment";

    public static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

    private static final String DIALOG_DATE = "DialogDate";

    private TextView bookUuid;

    private TextView bookName;

    private TextView bookDate;

    private UUID mUUID;

    private BookItem mBookItem;

    private Button mDateChangeBtn;

    private File mPhotoFile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_book, container, false);

        bookUuid = view.findViewById(R.id.book_uuid);
        bookName = view.findViewById(R.id.book_name);
        bookDate = view.findViewById(R.id.book_date);
        mDateChangeBtn = view.findViewById(R.id.DialogBtn);

//        mDateChangeBtn.setEnabled(false);
//        mDateChangeBtn.setText(bookDate.getText());
//        mDateChangeBtn.setOnClickListener(v -> {
//            FragmentManager fm = getParentFragmentManager();
////            DatePickerFragment dialog = new DatePickerFragment();
//            DatePickerFragment dialog = DatePickerFragment.newInstance(mBookItem.getCreateDate());
//            dialog.setTargetFragment(this, 0);
//            dialog.show(fm, DIALOG_DATE);
//        });


        /**
         * 用于发送隐式intent 的dialogFragment
         */
        mDateChangeBtn.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
//            DatePickerFragment dialog = new DatePickerFragment();
            ImplictIntentDialogFragment dialog = ImplictIntentDialogFragment.newInstance(mBookItem);
            dialog.setTargetFragment(this, 0);
            dialog.show(fm, DIALOG_DATE);
        });

        Bundle bundle = getArguments();
        if (bundle != null){
            mUUID = (UUID) bundle.getSerializable(ARG_BOOK_ID);
            mBookItem = BookList.getInstance(getActivity()).getItemByUuid(mUUID);
            bookUuid.setText(mBookItem.getUUID().toString());
            bookName.setText(mBookItem.getName());
            updateBookDate();
        }

        return view;
    }

    private void updateBookDate() {
        bookDate.setText(sSimpleDateFormat.format(mBookItem.getCreateDate()));
    }


    private static final String ARG_BOOK_ID = "book_id";


    public static BookItemDetailFragment newInstance(UUID uuid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_ID, uuid);

        BookItemDetailFragment fragment = new BookItemDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == 0){
            Date rdate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

//            Log.i(TAG, "返回的时间是： " + sSimpleDateFormat.format(rdate));

            mBookItem.setCreateDate(rdate);

            BookList.getInstance(getActivity()).updateBook(mBookItem);

            updateBookDate();
        }
    }

}
