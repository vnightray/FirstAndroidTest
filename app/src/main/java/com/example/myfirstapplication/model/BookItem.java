package com.example.myfirstapplication.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class BookItem implements Serializable {

    private static final Random random = new Random(47);

    private UUID mUUID;

    private String mName;

    private Date mCreateDate;

    private boolean mRead;

    private String mOwner;

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public BookItem(String name) {
        this.mName = name;
        this.mUUID = UUID.randomUUID();
        this.mCreateDate = new Date(System.currentTimeMillis() - random.nextInt(10) * 10000000);
    }

    public BookItem(UUID uuid){
        mUUID = uuid;
        mCreateDate = new Date();
    }

    public UUID getUUID() {
        return mUUID;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(Date createDate) {
        mCreateDate = createDate;
    }

    public boolean isRead() {
        return mRead;
    }

    public void setRead(boolean read) {
        mRead = read;
    }

    public String getPhotoFilename(){
        return "IMG_" + getUUID().toString() + ".jpg";
    }

    @Override
    public String toString() {
        return "BookItem{" +
                "mUUID=" + mUUID +
                ", mName='" + mName + '\'' +
                ", mCreateDate=" + mCreateDate +
                '}';
    }

}
