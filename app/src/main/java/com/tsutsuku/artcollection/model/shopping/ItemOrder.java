package com.tsutsuku.artcollection.model.shopping;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/2/13
 * @Description Content
 */

public class ItemOrder implements Parcelable{

    /**
     * orderId : 17021317083608148
     * farmId : 86000001
     * farmName : 店铺一
     * totalFee : 10.00
     * cashFee : 10.00
     * virtualFee : 0.00
     * shouldPay : 10
     * orderStatus : 1
     * deliveryStatus : 0
     * deliveryFee : 0.00
     * isComment : 0
     * orderTime : 2017-02-13 17:08:36
     * userNote :
     * consigneeName : 看看快快快
     * contactNumber : 18768113747
     * address : 北京市县密云县快快快12345k
     * items : [{"productId":"2","itemTitle":"普通(标准版)","itemCover":"http://yssc.pinnc.com/upload/2016/06/27/20160627112112576.png","itemBrief":"一次消费，十年享受，亲子惠享！","unitPrice":"10.00","buyAmount":"1"}]
     * deliveryInfo : 邮寄
     */

    private String orderId;
    private String farmId;
    private String farmName;
    private String totalFee;
    private String cashFee;
    private String virtualFee;
    private String shouldPay;
    private String orderStatus;
    private String deliveryStatus;
    private String deliveryFee;
    private String isComment;
    private String orderTime;
    private String userNote;
    private String consigneeName;
    private String contactNumber;
    private String address;
    private String deliveryInfo;
    private List<ItemsBean> items;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getCashFee() {
        return cashFee;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public String getVirtualFee() {
        return virtualFee;
    }

    public void setVirtualFee(String virtualFee) {
        this.virtualFee = virtualFee;
    }

    public String getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(String shouldPay) {
        this.shouldPay = shouldPay;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Parcelable{
        /**
         * productId : 2
         * itemTitle : 普通(标准版)
         * itemCover : http://yssc.pinnc.com/upload/2016/06/27/20160627112112576.png
         * itemBrief : 一次消费，十年享受，亲子惠享！
         * unitPrice : 10.00
         * buyAmount : 1
         */

        private String productId;
        private String itemTitle;
        private String itemCover;
        private String itemBrief;
        private String unitPrice;
        private String buyAmount;
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public String getItemCover() {
            return itemCover;
        }

        public void setItemCover(String itemCover) {
            this.itemCover = itemCover;
        }

        public String getItemBrief() {
            return itemBrief;
        }

        public void setItemBrief(String itemBrief) {
            this.itemBrief = itemBrief;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getBuyAmount() {
            return buyAmount;
        }

        public void setBuyAmount(String buyAmount) {
            this.buyAmount = buyAmount;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.productId);
            dest.writeString(this.itemTitle);
            dest.writeString(this.itemCover);
            dest.writeString(this.itemBrief);
            dest.writeString(this.unitPrice);
            dest.writeString(this.buyAmount);
            dest.writeString(this.orderId);
        }

        public ItemsBean() {
        }

        protected ItemsBean(Parcel in) {
            this.productId = in.readString();
            this.itemTitle = in.readString();
            this.itemCover = in.readString();
            this.itemBrief = in.readString();
            this.unitPrice = in.readString();
            this.buyAmount = in.readString();
            this.orderId = in.readString();
        }

        public static final Creator<ItemsBean> CREATOR = new Creator<ItemsBean>() {
            @Override
            public ItemsBean createFromParcel(Parcel source) {
                return new ItemsBean(source);
            }

            @Override
            public ItemsBean[] newArray(int size) {
                return new ItemsBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.farmId);
        dest.writeString(this.farmName);
        dest.writeString(this.totalFee);
        dest.writeString(this.cashFee);
        dest.writeString(this.virtualFee);
        dest.writeString(this.shouldPay);
        dest.writeString(this.orderStatus);
        dest.writeString(this.deliveryStatus);
        dest.writeString(this.deliveryFee);
        dest.writeString(this.isComment);
        dest.writeString(this.orderTime);
        dest.writeString(this.userNote);
        dest.writeString(this.consigneeName);
        dest.writeString(this.contactNumber);
        dest.writeString(this.address);
        dest.writeString(this.deliveryInfo);
        dest.writeTypedList(this.items);
    }

    public ItemOrder() {
    }

    protected ItemOrder(Parcel in) {
        this.orderId = in.readString();
        this.farmId = in.readString();
        this.farmName = in.readString();
        this.totalFee = in.readString();
        this.cashFee = in.readString();
        this.virtualFee = in.readString();
        this.shouldPay = in.readString();
        this.orderStatus = in.readString();
        this.deliveryStatus = in.readString();
        this.deliveryFee = in.readString();
        this.isComment = in.readString();
        this.orderTime = in.readString();
        this.userNote = in.readString();
        this.consigneeName = in.readString();
        this.contactNumber = in.readString();
        this.address = in.readString();
        this.deliveryInfo = in.readString();
        this.items = in.createTypedArrayList(ItemsBean.CREATOR);
    }

    public static final Creator<ItemOrder> CREATOR = new Creator<ItemOrder>() {
        @Override
        public ItemOrder createFromParcel(Parcel source) {
            return new ItemOrder(source);
        }

        @Override
        public ItemOrder[] newArray(int size) {
            return new ItemOrder[size];
        }
    };
}
