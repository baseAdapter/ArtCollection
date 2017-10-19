package com.tsutsuku.artcollection.model.shopping;

/**
 * @Author Tsutsuku
 * @Create 2017/3/19
 * @Description 用于RxBus传递本地Order状态改变
 */

public class OrderBean {
    private String orderId;
    private String orderStatus;
    private String deliveryStatus;
    private String totalFee;
    private String isComment;

    public OrderBean(String orderId, String deliveryStatus, String orderStatus, String totalFee, String isComment) {
        this.orderId = orderId;
        this.deliveryStatus = deliveryStatus;
        this.orderStatus = orderStatus;
        this.totalFee = totalFee;
    }

    public OrderBean() {
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
