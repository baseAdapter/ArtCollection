package com.tsutsuku.artcollection.common;

/**
 * @Author Sun Renwei
 * @Create 2016/12/28
 * @Description App Constants
 */

public class Constants {
    // 单次分享图片最多数
    public static final int MAX_SHARE_PIC = 9;
    public static final int PAGE_SIZE = 10;

    // 系统设置参数
    public static final String SERVER_URL = "server_url"; // 客服
    public static final String REG_URL = "reg_url"; // 注册协议
    public static final String INTEGRATE_URL = "integrate_url"; // 积分说明
    public static final String HELP_URL = "help_url"; // 帮助说明
    public static final String FEE_URL = "feli_url"; // 物货费率
    public static final String PAWN_URL = "diandang_url"; // 什么是典当
    public static final String FLOW_URL = "liuchen_url"; // 物货流程
    public static final String ABOUT_ME_URL = "aboutme_url"; // 关于我们
    public static final String TOPUP_UTL = "topup_url"; // 充值和提现协议
    public static final String JIANDING_URL = "jianding_url"; // 鉴定协议
    public static final String DEPOSIT_URL = "deposit_url"; // 保证金说明网站
    public static final String JINGPAI_URL = "jingpai_url"; // 竞拍协议

    public static final String MIN_WITHDRAW = "user_withdraw_min_money"; // 最低提现金额

    // 基础App参数
    public static final String USER_ID = "userId";
    public static final String NICK = "nick";
    public static final String SEX = "sex";
    public static final String AVATAR = "photo";
    public static final String MOBILE = "mobile";
    public static final String SIGN = "sign";
    public static final String BALANCE = "balance";

    // IM 参数
    public static final String IM_ID = "imId";

    // Shopping 参数
    public static final String AREA_LIST = "areaList";
    public static final String REJECT_REASON = "rejectReason";
    public static final String CANCEL_REASON = "cancelReason";

    public static class ShoppingStatus {
        public static final String ALL = "0"; // 全部
        public static final String UNPAID = "1"; // 待付款
        public static final String UNDELIVERY = "2"; // 待发货
        public static final String UNRECEIVE = "3"; // 待收货
        public static final String UNCOMMENT = "4"; // 待评价
    }

    // APP特殊参数
    public static final String NEWS_CATE_LIST = "newsCateList";
    public static final String VIDEO_CATE_LIST = "videoCateList";
    public static final String CUR_VIDEO_LIST = "curVideoList";
    public static final String CATE_MORE = "cateMore";

    public static class Share {
        public static final String PRODUCT = "shareProductUrl";
        public static final String AUCTION = "shareAuctionUrl";
        public static final String ACTIVITY = "shareActivityUrl";
        public static final String SHOP = "shareShopUrl";
        public static final String VIDEO = "shareVideoUrl";
        public static final String CIRClE = "shareCircleUrl";
        public static final String NEWS = "shareNewsUrl";
    }
    
    public static class Search{
        public static final String ALL = "0";
        public static final String VENDOR = "1";
        public static final String GOODS = "2";
        public static final String ACTIVITY = "3";
        public static final String LESSON = "4";
        public static final String LIVE = "5";
        public static final String NEWS = "6";
    }
    
    public static class Message{
        public static final String PAIMAI_GET = "101";
        public static final String PAIMAI_BACK = "102";
        public static final String PAIMAI_NOTBUY = "105";
        public static final String APPADMIN = "204";
        public static final String PRODUCT_ABOUT = "301";
        public static final String JIANYI_ABOUT = "401";
        public static final String ADMIN_NOTI = "501";
    }

    public static class Circle{
        public static final String FREE = "1";
        public static final String EXPERT = "2";
    }

    public static final int TYPE_TREASURE = 0;
    public static final int TYPE_IDENTIFY = 1;
    public static final int CIRCLE_DELETE = -1;

}
