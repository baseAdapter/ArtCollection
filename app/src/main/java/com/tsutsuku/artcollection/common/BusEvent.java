package com.tsutsuku.artcollection.common;

/**
 *
 * @Author Sun Renwei
 * @Create 2017/2/17
 * @Description App内部通信
 *
 */

public class BusEvent {
    // Shopping
    public static final String SHOPPING = "shopping";
    public static final String DEAL_FINISH = "dealFinish";

    public static final String BID = "bid";
    public static final String LIVE_MESSAGE = "liveMessage";

    public static final String CIRCLE = "circle";

    public static final String HOME_UI = "homeUI";

    public static class HomeUI{
        public static final int AUCTION = 1;
    }
}
