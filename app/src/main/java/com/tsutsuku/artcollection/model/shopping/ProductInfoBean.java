package com.tsutsuku.artcollection.model.shopping;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/25
 * @Description Content
 */

public class ProductInfoBean {

    /**
     * productId : 15
     * farmId : 9
     * farmName : 陈安敏安字款银油
     * farmBrief : 琥珀净水金蓝珀宝宝百家锁
     * farmCover : http://yssc.pinnc.com/upload/2017/02/17/20170217153331701.jpg
     * productName : fsa
     * productNo : SD148999813047
     * cover : http://yssc.pinnc.com/upload/2017/03/20/20170320162209340.jpg
     * brief : 45
     * specifications : 454
     * inventory : 40
     * sale : 5
     * totalPrice : 4.00
     * priceUnit : 元
     * unit :
     * useCoupon : 1
     * isAuction : 1
     * briefPics : ["http://yssc.pinnc.com/upload/2017/03/20/20170320162209340.jpg"]
     * detailUrl : http://yssc.pinnc.com/aweb/ProductDes.php?pid=15
     * parameterUrl : http://yssc.pinnc.com/aweb/ProductParameter.php?pid=15
     * helpUrl : http://yssc.pinnc.com/aweb/ProductHelp.php?pid=15
     * farm : {"hxAccount":{"uuid":"8469bd40-f4e1-11e6-806a-51f1bc57da23","username":"developfarm_9"}}
     * productInfo : {"9":{"couponId":0,"deliveryType":1,"deliveryFee":0,"userNote":"","items":[{"productId":15,"buyAmount":1}]}}
     * product_buy_url : http://yssc.pinnc.com/aweb/ProductHelp.php?pid=15
     * product_yzsj_url : http://yssc.pinnc.com/aweb/ProductHelp.php?pid=15
     * product_zpbz_url : http://yssc.pinnc.com/aweb/ProductHelp.php?pid=15
     * auctionInfo : {"productId":"15","beginTime":"2017-03-20 16:22:14","endTime":"2017-03-30 16:22:15","beginPrice":"0.01","currentPrice":"0.03","addPrice":"0.01","deposit":"0.01","delayPeriod":"1","bidCount":"2","viewCount":"110","lotMoney":"23.00","auctionState":"1","orderStatus":"0","orderId":"0","auctionUserCount":"1"}
     * auctionRecode : [{"userId":"44","nickName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","price":"0.03","addPrice":"0.01","addTime":"2017-03-23 19:04:11"},{"userId":"44","nickName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","price":"0.02","addPrice":"0.01","addTime":"2017-03-23 09:41:51"}]
     * chatRoom : {"groupId":"11114622550018","groupName":"fsa","photo":""}
     * isCollection : 0
     * amountInCart : 0
     * isFollow : 0
     */

    private String guzhiPrice;
    private String productId;
    private String farmId;
    private String farmName;
    private String farmBrief;
    private String farmCover;
    private String productName;
    private String productNo;
    private String cover;
    private String brief;
    private String specifications;
    private String inventory;
    private String sale;
    private String totalPrice;
    private String priceUnit;
    private String unit;
    private String useCoupon;
    private String isAuction;
    private String detailUrl;
    private String parameterUrl;
    private String helpUrl;
    private FarmBean farm;
    private String productInfo;
    private String product_buy_url;
    private String product_yzsj_url;
    private String product_zpbz_url;
    private AuctionInfoBean auctionInfo;
    private ChatRoomBean chatRoom;
    private int isCollection;
    private int amountInCart;
    private int isFollow;
    private List<String> briefPics;
    private List<AuctionRecodeBean> auctionRecode;

    public String getGuzhiPrice() {
        return guzhiPrice;
    }

    public void setGuzhiPrice(String guzhiPrice) {
        this.guzhiPrice = guzhiPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getFarmBrief() {
        return farmBrief;
    }

    public void setFarmBrief(String farmBrief) {
        this.farmBrief = farmBrief;
    }

    public String getFarmCover() {
        return farmCover;
    }

    public void setFarmCover(String farmCover) {
        this.farmCover = farmCover;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(String useCoupon) {
        this.useCoupon = useCoupon;
    }

    public String getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(String isAuction) {
        this.isAuction = isAuction;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getParameterUrl() {
        return parameterUrl;
    }

    public void setParameterUrl(String parameterUrl) {
        this.parameterUrl = parameterUrl;
    }

    public String getHelpUrl() {
        return helpUrl;
    }

    public void setHelpUrl(String helpUrl) {
        this.helpUrl = helpUrl;
    }

    public FarmBean getFarm() {
        return farm;
    }

    public void setFarm(FarmBean farm) {
        this.farm = farm;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getProduct_buy_url() {
        return product_buy_url;
    }

    public void setProduct_buy_url(String product_buy_url) {
        this.product_buy_url = product_buy_url;
    }

    public String getProduct_yzsj_url() {
        return product_yzsj_url;
    }

    public void setProduct_yzsj_url(String product_yzsj_url) {
        this.product_yzsj_url = product_yzsj_url;
    }

    public String getProduct_zpbz_url() {
        return product_zpbz_url;
    }

    public void setProduct_zpbz_url(String product_zpbz_url) {
        this.product_zpbz_url = product_zpbz_url;
    }

    public AuctionInfoBean getAuctionInfo() {
        return auctionInfo;
    }

    public void setAuctionInfo(AuctionInfoBean auctionInfo) {
        this.auctionInfo = auctionInfo;
    }

    public ChatRoomBean getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoomBean chatRoom) {
        this.chatRoom = chatRoom;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public int getAmountInCart() {
        return amountInCart;
    }

    public void setAmountInCart(int amountInCart) {
        this.amountInCart = amountInCart;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public List<String> getBriefPics() {
        return briefPics;
    }

    public void setBriefPics(List<String> briefPics) {
        this.briefPics = briefPics;
    }

    public List<AuctionRecodeBean> getAuctionRecode() {
        return auctionRecode;
    }

    public void setAuctionRecode(List<AuctionRecodeBean> auctionRecode) {
        this.auctionRecode = auctionRecode;
    }

    public static class FarmBean {

        /**
         * uuid : 8469bd40-f4e1-11e6-806a-51f1bc57da23
         * username : developfarm_9
         */

        private String uuid;
        private String username;
        private String disname;
        private String photo;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisname() {
            return disname;
        }

        public void setDisname(String disname) {
            this.disname = disname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }

    public static class AuctionInfoBean {
        /**
         * productId : 15
         * beginTime : 2017-03-20 16:22:14
         * endTime : 2017-03-30 16:22:15
         * beginPrice : 0.01
         * currentPrice : 0.03
         * addPrice : 0.01
         * deposit : 0.01
         * delayPeriod : 1
         * bidCount : 2
         * viewCount : 110
         * lotMoney : 23.00
         * auctionState : 1
         * orderStatus : 0
         * orderId : 0
         * auctionUserCount : 1
         */

        private String productId;
        private String beginTime;
        private String endTime;
        private String beginPrice;
        private String currentPrice;
        private String addPrice;
        private String deposit;
        private String delayPeriod;
        private String bidCount;
        private String viewCount;
        private String lotMoney;
        private String auctionState;
        private String orderStatus;
        private String orderId;
        private String auctionUserCount;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getBeginPrice() {
            return beginPrice;
        }

        public void setBeginPrice(String beginPrice) {
            this.beginPrice = beginPrice;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getAddPrice() {
            return addPrice;
        }

        public void setAddPrice(String addPrice) {
            this.addPrice = addPrice;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getDelayPeriod() {
            return delayPeriod;
        }

        public void setDelayPeriod(String delayPeriod) {
            this.delayPeriod = delayPeriod;
        }

        public String getBidCount() {
            return bidCount;
        }

        public void setBidCount(String bidCount) {
            this.bidCount = bidCount;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getLotMoney() {
            return lotMoney;
        }

        public void setLotMoney(String lotMoney) {
            this.lotMoney = lotMoney;
        }

        public String getAuctionState() {
            return auctionState;
        }

        public void setAuctionState(String auctionState) {
            this.auctionState = auctionState;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAuctionUserCount() {
            return auctionUserCount;
        }

        public void setAuctionUserCount(String auctionUserCount) {
            this.auctionUserCount = auctionUserCount;
        }
    }

    public static class ChatRoomBean {
        /**
         * groupId : 11114622550018
         * groupName : fsa
         * photo :
         */

        private String groupId;
        private String groupName;
        private String photo;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }

    public static class AuctionRecodeBean {
        /**
         * userId : 44
         * nickName : 小禹
         * photo : http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg
         * price : 0.03
         * addPrice : 0.01
         * addTime : 2017-03-23 19:04:11
         */

        private String userId;
        private String nickName;
        private String photo;
        private String price;
        private String addPrice;
        private String addTime;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
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

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof AuctionRecodeBean) {
                AuctionRecodeBean bean = (AuctionRecodeBean) obj;
                if (bean.getAddTime().equals(this.getAddTime()) && bean.getUserId().equals(this.getUserId())) {
                    return true;
                }
            }
            return false;
        }
    }
}
