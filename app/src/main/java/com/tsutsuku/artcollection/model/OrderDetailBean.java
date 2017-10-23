package com.tsutsuku.artcollection.model;

import java.util.List;

public class OrderDetailBean extends BaseModel{


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * orderId : 2017102210257101
         * userId : 364
         * addressId : 1000246
         * totalGolds : 22
         * orderState : 1
         * deliveryState : 1
         * deliveryType : 3
         * createTime : 2017-10-22 15:14:55
         * nums : 11
         * goodsName : 原生态星月菩提单圈手串
         * photo : upload/2017/10/17/20171017134549746.png
         * address : 天津市市辖县静海县记得接电话
         * consignee : 精神上的
         * phone : 2147483647
         */

        private String orderId;
        private String userId;
        private String addressId;
        private String totalGolds;
        private String orderState;
        private String deliveryState;
        private String deliveryType;
        private String createTime;
        private String nums;
        private String goodsName;
        private String photo;
        private String address;
        private String consignee;
        private String phone;

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

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getTotalGolds() {
            return totalGolds;
        }

        public void setTotalGolds(String totalGolds) {
            this.totalGolds = totalGolds;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
            this.orderState = orderState;
        }

        public String getDeliveryState() {
            return deliveryState;
        }

        public void setDeliveryState(String deliveryState) {
            this.deliveryState = deliveryState;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
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

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
