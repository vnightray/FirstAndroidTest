package com.example.myfirstapplication.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.model.BookItem;
import com.example.myfirstapplication.model.BookList;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ImplictIntentDialogFragment extends DialogFragment {

    private static final String ARG_ITEM = "ImplictIntent";

    private static final String TAG = "ImplictIntentDialogFragment";

    public static final int REQUEST_PHOTO = 2;

    private File mPhotoFile;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.twobutton, null);

        Button mbtn1 = view.findViewById(R.id.firstbtn);
        mbtn1.setText("send text");

        Button mbtn2 = view.findViewById(R.id.lastbtn);
        mbtn2.setText("take photo");


        BookItem item = null;

        Bundle args = getArguments();
        if (args == null){
            item = new BookItem("new world");
        }
        else {
            item = (BookItem) args.getSerializable(ARG_ITEM);
        }


        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        pickContact.addCategory(Intent.CATEGORY_HOME);

        // 使用PackageManager 来检查当前手机是否有联系人 等 应用，即是否存在可以处理该intent 的应用
        // 在本机无应用可以匹配该intent的情况下，保证程序不会崩溃，即禁用按钮
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) != null){

            mbtn1.setOnClickListener(v -> {
                startActivityForResult(pickContact, 1);
            });
        }

        mbtn1.setOnClickListener(v -> {
//                startActivityForResult(pickContact, 1);
            Uri uri = Uri.parse("tel:12423534");
            Intent tempIntent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(tempIntent);

        });


        // 使用相机拍摄图片，并保存在自己的私有空间。
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 1. 获取私有空间的文件File对象
        mPhotoFile = BookList.getInstance(getActivity()).getPhotoFile(item);

        // 2. 判断文件对象是否存在，以及 当前是否有相机程序可以处理该 intent
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mbtn2.setEnabled(canTakePhoto);

        mbtn2.setOnClickListener(v -> {

            // 3. 使用FileProvider.getUriForFile方法 将 File对象 转换为 uri对象
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.example.myfirstapplication.fileprovider", mPhotoFile);

            // 4. 将uri对象放在 intent 的携带参数中
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            // 5. 查询得到处理intent 的所有ResovleInfo
            List<ResolveInfo> cameraActivities = getActivity()
                    .getPackageManager().queryIntentActivities(captureImage,
                            PackageManager.MATCH_DEFAULT_ONLY);

            // 6. 给所有相机activity 赋予 访问（写入）uri 的权限
            for (ResolveInfo activity : cameraActivities){
                getActivity().grantUriPermission(activity.activityInfo.packageName,
                        uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            startActivityForResult(captureImage, REQUEST_PHOTO);
        });



        BookItem finalItem = item;
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("implict intent test")
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) -> {

                    // 用于发送intent
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_TEXT, finalItem.getUUID().toString());
                            intent.putExtra(Intent.EXTRA_SUBJECT, finalItem.getOwner());

                            // 用于给 隐式intent 选择器 设置标题， 但是好像没啥用
                            intent = Intent.createChooser(intent, "send message title");
                            startActivity(intent);
                        })
                .create();
    }

    public static ImplictIntentDialogFragment newInstance(BookItem item){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM, item);

        ImplictIntentDialogFragment fragment = new ImplictIntentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


//    public void sendResult(int resultCode, Date date){
//        if (getTargetFragment() == null){
//            return;
//        }
//        Intent intent = new Intent();
//
//
//        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == 1 && data != null){
            Uri contactUri = data.getData();

            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor cursor = getActivity().getContentResolver().query(
                    contactUri, queryFields, null, null, null
            );

            try {
                if (cursor.getCount() == 0){
                    Log.i("BookListFragment", "cursor count is 0");
                    return;
                }

                cursor.moveToFirst();
                String person = cursor.getString(0);
                Log.i("BookListFragment", "result is " + person);

            }finally {
                cursor.close();
            }
        }
    }
}
