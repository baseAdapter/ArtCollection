package com.tsutsuku.artcollection.model.shopping;

import android.os.Parcel;
import android.os.Parcelable;

import com.tsutsuku.artcollection.utils.Arith;

import java.util.Observable;
import java.util.Observer;

/**
 * @Author Tsutsuku
 * @Create 2017/2/7
 * @Description
 */

public class ItemGoods extends Observable implements Parcelable, Observer {

    /**
     * productId : 1
     * productAmount : 10
     * productName : 普通(体验版)
     * productPrice : 0.01
     * productBrief : 一次购买，四季采摘，超值体验！
     * productCover : /upload/2016/06/27/20160627112105644.png
     * inventory : 835
     */

    private String productId;
    private String productAmount;
    private String productName;
    private String productPrice;
    private String productBrief;
    private String productCover;
    private String inventory;
    private String isAuction;
    private boolean edit;
    private boolean checked;

    public String getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(String isAuction) {
        this.isAuction = isAuction;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        if (checked) {
            setChanged();
            notifyObservers(Arith.mul((Arith.sub(productAmount, this.productAmount)), productPrice));
        }
        setChanged();
        notifyObservers('3');
        this.productAmount = productAmount;
    }


    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        if (this.edit != edit){
            this.edit = edit;
            setChanged();
            notifyObservers('2');
        }
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductBrief() {
        return productBrief;
    }

    public void setProductBrief(String productBrief) {
        this.productBrief = productBrief;
    }

    public String getProductCover() {
        return productCover;
    }

    public void setProductCover(String productCover) {
        this.productCover = productCover;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }


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
            setChanged();
            notifyObservers((checked ? "" : "-") + Arith.mul(productAmount, productPrice));
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Boolean) {
            setChecked(true);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.productAmount);
        dest.writeString(this.productName);
        dest.writeString(this.productPrice);
        dest.writeString(this.productBrief);
        dest.writeString(this.productCover);
        dest.writeString(this.inventory);
        dest.writeByte(this.edit ? (byte) 1 : (byte) 0);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    public ItemGoods() {
    }

    protected ItemGoods(Parcel in) {
        this.productId = in.readString();
        this.productAmount = in.readString();
        this.productName = in.readString();
        this.productPrice = in.readString();
        this.productBrief = in.readString();
        this.productCover = in.readString();
        this.inventory = in.readString();
        this.edit = in.readByte() != 0;
        this.checked = in.readByte() != 0;
    }

    public static final Creator<ItemGoods> CREATOR = new Creator<ItemGoods>() {
        @Override
        public ItemGoods createFromParcel(Parcel source) {
            return new ItemGoods(source);
        }

        @Override
        public ItemGoods[] newArray(int size) {
            return new ItemGoods[size];
        }
    };
}
