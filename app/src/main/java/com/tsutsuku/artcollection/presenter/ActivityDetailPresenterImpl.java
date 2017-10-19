package com.tsutsuku.artcollection.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tsutsuku.artcollection.alipay.ALiPayUtils;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.ActivityDetailContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.im.ui.ChatActivity;
import com.tsutsuku.artcollection.live.player.NEVideoPlayerActivity;
import com.tsutsuku.artcollection.live.stream.MediaPreviewActivity;
import com.tsutsuku.artcollection.live.stream.StreamRepository;
import com.tsutsuku.artcollection.model.ActivityInfo;
import com.tsutsuku.artcollection.model.live.LiveStatusInfo;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.wxapi.WXRxBus;
import com.tsutsuku.artcollection.wxapi.WxPayConstants;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/03/18
 */

public class ActivityDetailPresenterImpl implements ActivityDetailContract.Presenter {
    private static final int UNJOIN = 0; // 未参加活动，报名中
    private static final int JOINED = 1; // 已参加活动，但直播未开始且非直播主
    private static final int STREAM = 2; // 已参加活动，是直播主
    private static final int LIVE = 3; // 已参加活动，直播开始，但非直播主
    private static final int END = 4; // 未参加活动，报名结束

    private ActivityDetailContract.View view;
    private String activityId;
    private ActivityInfo info;
    private LiveStatusInfo statusInfo;
    private Context context;
    private int status; // 活动状态

    public ActivityDetailPresenterImpl(Context context, String activityId) {
        this.context = context;
        this.activityId = activityId;
    }

    @Override
    public void attachView(ActivityDetailContract.View view) {
        this.view = view;
        getData();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.getActivitiesInfo");
        hashMap.put("activityId", activityId);
        hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    info = GsonUtils.parseJson(data.getString("info"), ActivityInfo.class);
                    view.setViews(info);

                    if ("0".equals(info.getStatus())) {
                        if (info.getIsJoin() == 0) {
                            status = UNJOIN;
                        } else {
                            if ("2".equals(info.getCtype())) {
                                getLiveStatus();
                            } else {
                                status = JOINED;
                            }
                        }
                    } else {
                        status = END;
                    }



                    view.setLiveStatus(status);
                }

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }


    @Override
    public void apply(final String payType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.payForProducts");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("payType", "1");
        hashMap.put("productsInfo", info.getProductInfo());
        hashMap.put("otherType", payType);
        hashMap.put("otherFee", "balance".equals(payType) ? "0" : info.getUseMoney());
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {

                    switch (payType) {
                        case "alipay": {
                            JSONObject jo = data.getJSONObject("info");
                            JSONObject tradeNo = jo.getJSONObject("tradeNo");
                            {
                                new ALiPayUtils(tradeNo.getString("parmsstr"), tradeNo.getString("sign"), context, new ALiPayUtils.PaySucceed() {
                                    @Override
                                    public void onSuccess() {
                                        payed();
                                    }
                                }, new ALiPayUtils.PayAffirm() {
                                    @Override
                                    public void onAffirm() {
                                    }
                                }, new ALiPayUtils.PayDefeated() {
                                    @Override
                                    public void onDefeated() {
                                    }
                                }).submitSign();
                            }
                        }
                        break;
                        case "wxpay": {
                            JSONObject jo = data.getJSONObject("info");
                            JSONObject tradeNojo = jo.getJSONObject("tradeNo");

                            PayReq req = new PayReq();
                            IWXAPI api;
                            api = WXAPIFactory.createWXAPI(context, null);
                            // 将该app注册到微信
                            api.registerApp(WxPayConstants.WXAPP_ID);
                            if (api.openWXApp()) {
                                req.appId = tradeNojo.getString("appid");
                                req.partnerId = tradeNojo.getString("partnerid");
                                req.prepayId = tradeNojo.getString("prepayid");
                                req.packageValue = tradeNojo.getString("package");
                                req.nonceStr = tradeNojo.getString("noncestr");
                                req.timeStamp = tradeNojo.getString("timestamp");
                                req.sign = tradeNojo.getString("sign");
                                // genPayReq();
                                api.sendReq(req);
                            }

                            new WXRxBus() {
                                @Override
                                public void cancel() {

                                }

                                @Override
                                public void fail() {

                                }

                                @Override
                                public void success() {
                                    payed();
                                }
                            };
                        }
                        break;
                        case "balance": {
                            payed();
                        }
                        break;
                        default:
                            break;
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void payed() {
        status = JOINED;
        view.setLiveStatus(status);
    }

    @Override
    public void openLive() {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
            LoginActivity.launch(context);
            return;
        }
        switch (status) {
            case UNJOIN: {
                if (Float.valueOf(info.getUseMoney()) == 0) {
                    apply("balance");
                } else {
                    view.showPayDialog();
                }
            }
            break;
            case STREAM: {
                view.showStreamDialog();
            }
            break;
            case LIVE: {
                NEVideoPlayerActivity.launch(context, info.getChatRoom().getGroupId(), info.getActivityId(), info.getVcloud().getHttpPullUrl());
            }
            break;
        }
    }

    @Override
    public void follow() {
        FollowRepository.follow(info.getFarmInfo().getIsFollow() == 1, "1", info.getFarmId(), new FollowRepository.OnFollowListener() {
            @Override
            public void success(boolean follow) {
                info.getFarmInfo().setIsFollow(follow ? 1 : 0);
                view.setFollow(follow);
            }
        });
    }

    @Override
    public void share() {
        ShareUtils.showShare(context, info.getCoverPhoto(), info.getActivityName(), SharedPref.getSysString(Constants.Share.ACTIVITY) + activityId);
    }

    @Override
    public void chat() {
        ChatActivity.launch(context, info.getFarmInfo().getHxAccount().getUsername(),
                "",
                info.getFarmInfo().getFarmName(),
                info.getFarmInfo().getCoverPic());

    }

    @Override
    public void stream(boolean needRecord) {
        StreamRepository.setRecord(info.getVcloud().getCid(), needRecord, info.getActivityId(), info.getPushUserId());
        MediaPreviewActivity.launch(context, info.getVcloud().getPushUrl(), "SD", info.getActivityId(), info.getPushUserId());
    }

    private void getLiveStatus() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.getChannelStats");
        hashMap.put("cId", info.getVcloud().getCid());
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    statusInfo = GsonUtils.parseJson(data.getJSONObject("info").getString("ret"), LiveStatusInfo.class);

                    if (info.getCanPush() == 1) {
                        status = STREAM;
                    } else if (info.getIsJoin() == 1) {
                        if (statusInfo.getStatus() == 1) {
                            status = LIVE;
                        } else {
                            status = JOINED;
                        }
                    }

                    view.setLiveStatus(status);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }


}