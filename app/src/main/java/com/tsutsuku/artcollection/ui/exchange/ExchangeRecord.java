package com.tsutsuku.artcollection.ui.exchange;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/8.
 */

public class ExchangeRecord implements Serializable{
    /**
     * orderId : 2017102148539799
     * addressId : 1000246
     * goldAmount : 2
     * outOrderId : 35
     * createTime : 2017-10-21 10:20:48
     * nums : 1
     * name : 原生态星月菩提单圈手串
     * coverPhoto : /upload/2017/10/17/20171017134549746.png
     */

    private String orderId;
    private String addressId;
    private String goldAmount;
    private String outOrderId;
    private String createTime;
    private String nums;
    private String name;
    private String coverPhoto;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(String goldAmount) {
        this.goldAmount = goldAmount;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }


}
