package com.example.myfirstapplication.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ComponentActivity;
import androidx.core.content.ContextCompat;

import com.example.myfirstapplication.DialogActivity;
import com.example.myfirstapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 在api29，android10的sumsang s9测试完成的external storage下，sdcard文件目录下创建和删除文件（夹），
 * 同时测试了，使用contentresolver，对共享目录下所有文件进行搜查的过程。关键在于运行时动态申请访问权限
 */

public class ExternStorageVisitActivity extends AppCompatActivity {

    private Button contentBtn;

    private TextView contentTv;

    private static String TAG = "ExternStorageVisitActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_and_textview);
        init(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(@Nullable Bundle savedInstanceState){
        contentBtn = findViewById(R.id.contentbtn);
        contentTv = findViewById(R.id.contenttv);
        if (savedInstanceState != null){
            contentTv.setText(savedInstanceState.getString("tvcontent", "lalalalalala"));
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)){
            contentBtn.setEnabled(false);
        }
        contentBtn.setOnClickListener((v) -> {
            File sdcardDir = new File("/sdcard");
            contentTv.setText(sdcardDir.getAbsolutePath());


//            File newDir = new File(sdcardDir.getAbsolutePath() + "/newDir/");

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                if (!newDir.exists()){
//                    newDir.mkdirs();
//                }
//                else {
//
//                }
            }

            // 文件创建于 /storage/emulated/0/Android/data/com.example.myfirstapplication/files/test.txt
//            File testtxt = new File(getExternalFilesDir(null), "test.txt");
//            if (testtxt.exists()){
//                Log.i(TAG, testtxt.getAbsolutePath());
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new FileReader(testtxt));
//                    String contenttxt = bufferedReader.readLine();
//                    System.out.println(contenttxt);
//                    Log.i(TAG, contenttxt);
//                    bufferedReader.close();
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//            else {
//                try {
//                    FileOutputStream fos = new FileOutputStream(testtxt);
//                    fos.write("wahahaha".getBytes("utf-8"));
//                    fos.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


            // 测试在sdcard目录下创建文件或文件夹
            // 成功于sdcard 目录下创建文件夹
            String sdPath = getSDPath();
            Log.i(TAG, sdPath);
            File dirFile = new File(sdPath, "/testdir");
            // 创建文件夹
            if (!dirFile.exists()){
                boolean success = dirFile.mkdirs();
                Log.i(TAG, "文件夹创建结果" + success);
            }
            else {
                Log.i(TAG, "文件夹已存在");
            }

            // 创建文件 成功
            File mytestfile = new File(dirFile.getAbsolutePath(), "mytest.txt");
            if (!mytestfile.exists()){
                Log.i(TAG,"mytestfile的全路径为" + mytestfile.getAbsolutePath());
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mytestfile));
                    bufferedWriter.write("wahahaha!!!");
                    bufferedWriter.close();

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                Log.i(TAG, "文件mytestfile已存在");
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(mytestfile));
                    String mycontent = bufferedReader.readLine();
                    Log.i(TAG, "mycontent: " + mycontent);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }





//            getDocumentData();
            // 查询无结果的关键在于 1， manifest文件中需申明需要的权限， 2. 在运行时动态检查是否获取权限，若没有获取，则需动态获取
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            }
//            getDocument2();
//                queryBooks();


//            try {
//                if (sdcardDir.isDirectory()){
//
//                    for (File file : sdcardDir.listFiles()) {
//                        if (null == file || !file.exists()){
//                            return;
//                        }
//                        System.out.println(file.getName());
//                    }
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//            findFiles(sdcardDir);
        });


        Button btn2 = findViewById(R.id.contentbtn2);

        // 测试启动另一个activity
//        btn2.setOnClickListener(v -> {
//            Intent intent = new Intent(this, DialogActivity.class);
//            startActivity(intent);
//        });

        btn2.setOnClickListener(v -> {
            int cx = btn2.getWidth() / 2;
            int cy = btn2.getHeight() / 2;
            float radius = btn2.getWidth();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Animator anim = ViewAnimationUtils.createCircularReveal(btn2, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation, boolean isReverse) {
                        super.onAnimationEnd(animation, isReverse);
                        btn2.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            }
        });
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = isSdCardAvailable();// 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        return sdDir.toString();
        }
        return "null";
    }


    private void queryBooks(){
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE};

        Cursor cursor = getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " like ?" + " AND " +
                MediaStore.Files.FileColumns.SIZE + " >= ?",
                new String[]{"%.txt", "512000"},
                null
        );

        if (cursor != null && cursor.moveToFirst()){
            do {
                int ctitle = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                int csize = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                long size = cursor.getLong(csize);
                String title = cursor.getString(ctitle);
                int dot = title.lastIndexOf('/');
                String name = title.substring(dot + 1);
                System.out.println(name);
            } while (cursor.moveToNext());

        }

        cursor.close();

    }



    private double getRealSize(long size){
        return size / 1024.0 ;
    }


    public void getDocumentData(){
        String[] columns = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.PARENT, MediaStore.Files.FileColumns.DATA};

        String select = "(_data LIKE '%.jpg')";

        String select2 = MediaStore.Files.FileColumns.MEDIA_TYPE + " = '" + "text/plain" + "'";

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns,
                select, null, null);

        int columnIndexOrThrow_Data = 0;
        int columnFileSize = 0;
        if (cursor != null){
            columnIndexOrThrow_Data = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            columnFileSize = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
        }
        else {
            System.out.println("cursor is null!!");
        }

        if (cursor != null){
            while (cursor.moveToNext()){
                String path = cursor.getString(columnIndexOrThrow_Data);
                String fileSize = cursor.getString(columnFileSize);
                System.out.println(path + " --- " + fileSize);
            }
        }

        cursor.close();
    }

    public void getDocument2(){
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        // cache

        String bookpath = createRootPath(this);
        System.out.println(bookpath);

        // 查询后缀名为txt等
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " not like ? and ("
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? )",
                new String[]{"%" + bookpath + "%",
                        "%" + ".txt",
                        "%" + ".pdf",
                        "%" + ".epub",
                        "%" + ".chm"}, null
        );

        if (cursor != null && cursor.moveToFirst()){
            int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
            int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

            do {
                String path = cursor.getString(dataindex);

                System.out.println(path);

                int dot = path.lastIndexOf("/");
                String name = path.substring(dot + 1);
                if (name.lastIndexOf(".") > 0)
                    name = name.substring(0, name.lastIndexOf("."));

            } while (cursor.moveToNext());

            cursor.close();

        }


    }


    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    // 应对屏幕翻转造成 textview 内容重置 因此需要重写onSaveInstanceState (很重要，经常用到！！！)
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tvcontent", contentTv.getText().toString());
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause....");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop....");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestory....");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart....");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume....");
    }
}
