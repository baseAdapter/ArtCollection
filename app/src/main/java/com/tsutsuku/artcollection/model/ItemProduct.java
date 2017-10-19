package com.tsutsuku.artcollection.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Observable;

/**
 * @Author Sun Renwei
 * @Create 2017/1/20
 * @Description Content
 */

public class ItemProduct extends Observable implements Parcelable {

    /**
     * productId : 34
     * productName : 测拼
     * pic : http://yssc.pinnc.com/upload/2016/06/27/20160627111640799.png
     * totalPrice : 0.01
     * priceUnit : 元
     * isAuction : 0
     */

    private String productId;
    private String productName;
    private String pic;
    private String totalPrice;
    private String priceUnit;
    private int isAuction;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (!checked) {
            setChanged();
            notifyObservers();
        }
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public int getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(int isAuction) {
        this.isAuction = isAuction;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.productName);
        dest.writeString(this.pic);
        dest.writeString(this.totalPrice);
        dest.writeString(this.priceUnit);
        dest.writeInt(this.isAuction);
    }

    public ItemProduct() {
    }

    protected ItemProduct(Parcel in) {
        this.productId = in.readString();
        this.productName = in.readString();
        this.pic = in.readString();
        this.totalPrice = in.readString();
        this.priceUnit = in.readString();
        this.isAuction = in.readInt();
    }

    public static final Creator<ItemProduct> CREATOR = new Creator<ItemProduct>() {
        @Override
        public ItemProduct createFromParcel(Parcel source) {
            return new ItemProduct(source);
        }

        @Override
        public ItemProduct[] newArray(int size) {
            return new ItemProduct[size];
        }
    };
}
