package com.tsutsuku.artcollection.model.auction;

import android.os.Parcel;
import android.os.Parcelable;

import com.tsutsuku.artcollection.utils.Arith;

import java.util.Observable;
import java.util.Observer;

/**
 * @Author Tsutsuku
 * @Create 2017/4/3
 * @Description
 */

public class ItemDealProduct extends Observable implements Parcelable, Observer{
    /**
     * productId : 8
     * productPrice : 0.15
     * sucessTime : 2017-03-08 08:57:03
     * endPayTime : 2017-03-15 08:57:03
     * productName : 拍卖
     * pic : http://yssc.pinnc.com/upload/2017/03/03/20170303085606586.jpg
     */

    private String productId;
    private String productPrice;
    private String sucessTime;
    private String endPayTime;
    private String productName;
    private String pic;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;

            setChanged();
            notifyObservers(true);
            setChanged();
            notifyObservers('1');
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Boolean) {
            setChecked(true);
        }
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSucessTime() {
        return sucessTime;
    }

    public void setSucessTime(String sucessTime) {
        this.sucessTime = sucessTime;
    }

    public String getEndPayTime() {
        return endPayTime;
    }

    public void setEndPayTime(String endPayTime) {
        this.endPayTime = endPayTime;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.productPrice);
        dest.writeString(this.sucessTime);
        dest.writeString(this.endPayTime);
        dest.writeString(this.productName);
        dest.writeString(this.pic);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    public ItemDealProduct() {
    }

    protected ItemDealProduct(Parcel in) {
        this.productId = in.readString();
        this.productPrice = in.readString();
        this.sucessTime = in.readString();
        this.endPayTime = in.readString();
        this.productName = in.readString();
        this.pic = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<ItemDealProduct> CREATOR = new Creator<ItemDealProduct>() {
        @Override
        public ItemDealProduct createFromParcel(Parcel source) {
            return new ItemDealProduct(source);
        }

        @Override
        public ItemDealProduct[] newArray(int size) {
            return new ItemDealProduct[size];
        }
    };
}
