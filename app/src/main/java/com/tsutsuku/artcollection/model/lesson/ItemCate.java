package com.tsutsuku.artcollection.model.lesson;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author Tsutsuku
 * @Create 2017/3/5
 * @Description
 */

public class ItemCate implements Parcelable{

    /**
     * cateId : -1
     * cateName : 最新
     */

    private String cateId;
    private String cateName;

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemCate)) {
            return false;
        }

        ItemCate item = (ItemCate) obj;
        if (item.getCateId().equals(getCateId()) && item.getCateName().equals(getCateName())) {
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cateId);
        dest.writeString(this.cateName);
    }

    public ItemCate() {
    }

    protected ItemCate(Parcel in) {
        this.cateId = in.readString();
        this.cateName = in.readString();
    }

    public static final Creator<ItemCate> CREATOR = new Creator<ItemCate>() {
        @Override
        public ItemCate createFromParcel(Parcel source) {
            return new ItemCate(source);
        }

        @Override
        public ItemCate[] newArray(int size) {
            return new ItemCate[size];
        }
    };
}
