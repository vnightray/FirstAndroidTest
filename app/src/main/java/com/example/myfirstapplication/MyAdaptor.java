package com.example.myfirstapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class MyAdaptor extends BaseAdapter {
    static class ViewHolder{
        TextView textView1;
        TextView textView2;
    }
    private LayoutInflater mInflater;   // 布局填充器
    private String[] mDataSource;       // 数据源数组
    private String[] mIcons;            // 与数据源数组对应的图标id， 这里因为没有图标，直接复制一份DataSource
    private int mResource;              // 列表项布局文件
    private Context mContext;           // 所在上下文

    public MyAdaptor(Context context, int resource, String[] dataSource, String[] icons){
        mContext = context;
        mResource = resource;
        mDataSource = dataSource;
        mIcons = icons;
        // 通过上下文对象创建布局填充器
        mInflater = LayoutInflater.from(context);
    }

    // 返回总数据源中总的记录数
    @Override
    public int getCount() {
        return mDataSource.length;
    }

    // 根据选择列表项位置，返回列表项所需数据
    @Override
    public Object getItem(int i) {
        return mDataSource[i];
    }

    // 根据选择列表项位置，返回列表项id
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = mInflater.inflate(mResource, null);
            holder = new ViewHolder();
            holder.textView1 = view.findViewById(R.id.textview1);
            holder.textView2 = view.findViewById(R.id.textview2);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textView1.setText(mDataSource[i]);
        holder.textView2.setText(mIcons[i]);
        return view;
    }
}















