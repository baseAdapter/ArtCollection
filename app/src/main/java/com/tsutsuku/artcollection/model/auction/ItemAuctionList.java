package com.tsutsuku.artcollection.model.auction;

/**
 * @Author Tsutsuku
 * @Create 2017/3/19
 * @Description
 */

public class ItemAuctionList {
    /**
     * productId : 8
     * price : 0.15
     * addPrice : 0.01
     * currentPrice : 0.15
     * auctionState : 3
     * productName : 拍卖
     * pic : http://yssc.pinnc.com/upload/2017/03/03/20170303085606586.jpg
     * totalPrice : 34.00
     * priceUnit : 元
     * myAuctionCount : 3
     */

    private String productId;
    private String price;
    private String addPrice;
    private String currentPrice;
    private String auctionState;
    private String productName;
    private String pic;
    private String totalPrice;
    private String priceUnit;
    private String myAuctionCount;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(String addPrice) {
        this.addPrice = addPrice;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getAuctionState() {
        return auctionState;
    }

    public void setAuctionState(String auctionState) {
        this.auctionState = auctionState;
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

    public String getMyAuctionCount() {
        return myAuctionCount;
    }

    public void setMyAuctionCount(String myAuctionCount) {
        this.myAuctionCount = myAuctionCount;
    }
}
