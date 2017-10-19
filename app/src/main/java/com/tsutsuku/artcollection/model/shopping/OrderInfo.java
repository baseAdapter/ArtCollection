package com.tsutsuku.artcollection.model.shopping;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/3/16
 * @Description 订单详情
 */

public class OrderInfo {

    /**
     * orderId : 17031521333496211
     * userId : 43
     * farmId : 7
     * farmName : 百饰工艺
     * totalFee : 223.00
     * cashFee : 223.00
     * virtualFee : 0.00
     * couponFee : 0.00
     * shouldPay : 223
     * orderStatus : 1
     * deliveryStatus : 1
     * isComment : 0
     * orderTime : 2017-03-15 21:33:34
     * payTime : 2017-03-15 21:33:35
     * deliveryTime :
     * deliveryType : 1
     * deliveryFee : 0.00
     * userNote :
     * consigneeName : 看看快快快
     * contactNumber : 18768113747
     * address : 北京市县密云县快快快12345k
     * dealTime :
     * receiveTime :
     * rejectedTime :
     * rejectedReason :
     * rejectedNote :
     * rejectedOkTime :
     * items : [{"productId":"4","itemTitle":"谢志高《仕女》","itemCover":"http://yssc.pinnc.com/upload/2017/02/17/20170217150239408.jpg","itemBrief":"","unitPrice":"223.00","buyAmount":"1","specifications":""}]
     * deliveryInfo : 自提
     * cashBalance : 9466.90
     * virtualBalance : 0.00
     * payTimeLeft : 0
     */

    private String orderId;
    private String userId;
    private String farmId;
    private String farmName;
    private String totalFee;
    private String cashFee;
    private String virtualFee;
    private String couponFee;
    private String shouldPay;
    private String orderStatus;
    private String deliveryStatus;
    private String isComment;
    private String orderTime;
    private String payTime;
    private String deliveryTime;
    private String deliveryType;
    private String deliveryFee;
    private String userNote;
    private String consigneeName;
    private String contactNumber;
    private String address;
    private String dealTime;
    private String receiveTime;
    private String rejectedTime;
    private String rejectedReason;
    private String rejectedNote;
    private String rejectedOkTime;
    private String deliveryInfo;
    private String cashBalance;
    private String virtualBalance;
    private String payTimeLeft;
    private List<ItemsBean> items;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
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

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getRejectedTime() {
        return rejectedTime;
    }

    public void setRejectedTime(String rejectedTime) {
        this.rejectedTime = rejectedTime;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public String getRejectedNote() {
        return rejectedNote;
    }

    public void setRejectedNote(String rejectedNote) {
        this.rejectedNote = rejectedNote;
    }

    public String getRejectedOkTime() {
        return rejectedOkTime;
    }

    public void setRejectedOkTime(String rejectedOkTime) {
        this.rejectedOkTime = rejectedOkTime;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }

    public String getVirtualBalance() {
        return virtualBalance;
    }

    public void setVirtualBalance(String virtualBalance) {
        this.virtualBalance = virtualBalance;
    }

    public String getPayTimeLeft() {
        return payTimeLeft;
    }

    public void setPayTimeLeft(String payTimeLeft) {
        this.payTimeLeft = payTimeLeft;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * productId : 4
         * itemTitle : 谢志高《仕女》
         * itemCover : http://yssc.pinnc.com/upload/2017/02/17/20170217150239408.jpg
         * itemBrief :
         * unitPrice : 223.00
         * buyAmount : 1
         * specifications :
         */

        private String productId;
        private String itemTitle;
        private String itemCover;
        private String itemBrief;
        private String unitPrice;
        private String buyAmount;
        private String specifications;

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

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }
    }
}
