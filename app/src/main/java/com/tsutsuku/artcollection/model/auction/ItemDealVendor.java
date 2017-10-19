package com.tsutsuku.artcollection.model.auction;

import android.os.Parcel;
import android.os.Parcelable;

import com.tsutsuku.artcollection.model.shopping.ItemGoods;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @Author Tsutsuku
 * @Create 2017/4/3
 * @Description
 */

public class ItemDealVendor extends Observable implements Parcelable, Observer {

    /**
     * farmId : 7
     * farmName : 百饰工艺
     * products : [{"productId":"8","productPrice":"0.15","sucessTime":"2017-03-08 08:57:03","endPayTime":"2017-03-15 08:57:03","productName":"拍卖","pic":"http://yssc.pinnc.com/upload/2017/03/03/20170303085606586.jpg"},{"productId":"12","productPrice":"0.17","sucessTime":"2017-03-30 08:38:03","endPayTime":"2017-04-06 08:38:03","productName":"33","pic":"http://yssc.pinnc.com/upload/2017/03/25/20170325194332269.png"},{"productId":"16","productPrice":"0.08","sucessTime":"2017-03-25 21:53:04","endPayTime":"2017-04-01 21:53:04","productName":"拍","pic":"http://yssc.pinnc.com/upload/2017/03/25/20170325194300343.png"}]
     */

    private String farmId;
    private String farmName;
    private List<ItemDealProduct> products;
    private boolean checked;

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public List<ItemDealProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ItemDealProduct> products) {
        this.products = products;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            setChanged();
            notifyObservers('1');
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Boolean) {
            ItemDealProduct goods = (ItemDealProduct) o;
            if (goods.isChecked()) {
                boolean temp = true;
                for (ItemDealProduct item : this.products) {
                    temp &= item.isChecked();
                }
                if (temp) {
                    setChecked(true);
                }
            } else {
                setChecked(false);
            }
        }
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.farmId);
        dest.writeString(this.farmName);
        dest.writeList(this.products);
    }

    public ItemDealVendor() {
    }

    protected ItemDealVendor(Parcel in) {
        this.farmId = in.readString();
        this.farmName = in.readString();
        this.products = new ArrayList<ItemDealProduct>();
        in.readList(this.products, ItemDealProduct.class.getClassLoader());
    }

    public static final Creator<ItemDealVendor> CREATOR = new Creator<ItemDealVendor>() {
        @Override
        public ItemDealVendor createFromParcel(Parcel source) {
            return new ItemDealVendor(source);
        }

        @Override
        public ItemDealVendor[] newArray(int size) {
            return new ItemDealVendor[size];
        }
    };
}
