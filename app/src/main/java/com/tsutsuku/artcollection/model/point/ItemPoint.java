package com.tsutsuku.artcollection.model.point;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description Content
 */

public class ItemPoint implements Parcelable{

    /**
     * id : 3
     * name : 霸气精雕貔貅挂件
     * integrate : 34
     * inventory : 68
     * brief : 34
     * coverPhoto : http://yssc.pinnc.com/upload/2017/02/18/20170218110305317.jpg
     * detailUrl : http://yssc.pinnc.com/aweb/ExchangeDetail.php?id=3
     */

    private String id;
    private String name;
    private String integrate;
    private String inventory;
    private String brief;
    private String coverPhoto;
    private String detailUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntegrate() {
        return integrate;
    }

    public void setIntegrate(String integrate) {
        this.integrate = integrate;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.integrate);
        dest.writeString(this.inventory);
        dest.writeString(this.brief);
        dest.writeString(this.coverPhoto);
        dest.writeString(this.detailUrl);
    }

    public ItemPoint() {
    }

    protected ItemPoint(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.integrate = in.readString();
        this.inventory = in.readString();
        this.brief = in.readString();
        this.coverPhoto = in.readString();
        this.detailUrl = in.readString();
    }

    public static final Creator<ItemPoint> CREATOR = new Creator<ItemPoint>() {
        @Override
        public ItemPoint createFromParcel(Parcel source) {
            return new ItemPoint(source);
        }

        @Override
        public ItemPoint[] newArray(int size) {
            return new ItemPoint[size];
        }
    };
}
