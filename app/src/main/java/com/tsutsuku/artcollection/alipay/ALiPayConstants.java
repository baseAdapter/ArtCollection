package com.tsutsuku.artcollection.alipay;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.utils.SharedPref;


/**
 * @Author Sun Renwei
 * @Create 2016/9/26
 * @Description 支付宝常量
 */
public class ALiPayConstants {
    public static final String RSA_PRIVATE = "";

    public static final int SDK_PAY_FLAG = 1;

    public static final String SELLER = "";

    public static final String PARTNER = "";

    public static final String ALIPAY_CHARGE_NOTIFY_URL = "";

    public static final String alipay_subject = "微学堂-付款";

    public static final String PAY_CANCEL = "取消支付";

    public static final String PAY_FAILED = "支付失败";

    public static final String PAY_SUCCESS = "支付成功";

    public static String getUserId() {
        return SharedPref.getString(Constants.USER_ID);
    }
}
