package com.tsutsuku.artcollection.presenter.circle;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.circle.ShareBaseContract;
import com.tsutsuku.artcollection.contract.circle.ShareContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.model.CateBean;
import com.tsutsuku.artcollection.model.ItemExport;
import com.tsutsuku.artcollection.ui.circle.ExportListActivity;
import com.tsutsuku.artcollection.ui.common.CateSelectActivity;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author Sun Renwei
 * @Create 2017/1/10
 * @Description
 */

public class SharePresenterImpl extends ShareBasePresenterImpl implements ShareContract.Presenter {

    private static final String strCircleBlank = "动态内容为空";

    private static final int REQUEST_TYPE = 4;
    private static final int REQUEST_EXPERT = 5;

    public static final int TYPE_TREASURE = 0;
    public static final int TYPE_IDENTIFY = 1;

    private static final int PUBLISH_TYPE_PAID = 0;
    private static final int PUBLISH_TYPE_FREE = 1;

    private String typeId;
    private String expertId;
    private int publishType;

    public SharePresenterImpl(int type) {
        this.type = type;
    }

    @Override
    public void attachView(ShareBaseContract.View view) {
        super.attachView(view);
        if (type == TYPE_IDENTIFY) {
            ((ShareContract.View) view).setTypeIdentify();
        }
    }

    @Override
    public void share(String title, String content, String useMoney, boolean agree) {
        if (TextUtils.isEmpty(title)) {
            ToastUtils.showMessage(R.string.title_blank);
        } else if (TextUtils.isEmpty(content)) {
            ToastUtils.showMessage(R.string.content_blank);
        } else if (uploadList.size() == 0) {
            ToastUtils.showMessage(R.string.pic_blank);
        } else if (typeId == null) {
            ToastUtils.showMessage(R.string.type_blank);
        } else if (type == TYPE_TREASURE) {
            shareImpl(title, content, "0");
        } else if (publishType == PUBLISH_TYPE_FREE) {
            shareImpl(title, content, "0");
        } else if (TextUtils.isEmpty(expertId)) {
            ToastUtils.showMessage(R.string.expert_id_blank);
        } else if (!agree) {
            ToastUtils.showMessage(R.string.please_read_identify_and_agree);
        } else {
            shareImpl(title, content, useMoney);
        }
    }

    private void shareImpl(String title, String content, String useMoney) {
        view.showProgressDialog();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.publishMessage");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("ctype", String.valueOf(type));
        if (type == TYPE_IDENTIFY) {
            if (publishType == PUBLISH_TYPE_PAID) {
                hashMap.put("expertUserId", expertId);
            }

            hashMap.put("isFree", publishType == PUBLISH_TYPE_FREE ? "1" : "0");
        }
        hashMap.put("spcateId", typeId);
        hashMap.put("title", title);
        hashMap.put("content", content);
        hashMap.put("useMoney", useMoney);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, uploadList, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.finish();
                    BusBean bean = new BusBean(type);
                    if (type == TYPE_IDENTIFY) {
                        bean.setExt(publishType == PUBLISH_TYPE_FREE ? "0" : "1");
                    }
                    RxBus.getDefault().post(BusEvent.CIRCLE, bean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    @Override
    public void setPublishType(int publishType) {
        if (this.publishType != publishType) {
            this.publishType = publishType;
            if (publishType == PUBLISH_TYPE_PAID) {
                expertId = null;
            }
            ((ShareContract.View) view).setPublishType(publishType == PUBLISH_TYPE_FREE);
        }

    }

    @Override
    public void getType() {
        CateSelectActivity.launchTypeMenu((Activity) context, REQUEST_TYPE);
    }

    @Override
    public void getExpert(boolean isFree) {
        ExportListActivity.launch((Activity) view.getContext(), REQUEST_EXPERT, isFree);
    }

    @Override
    public void parseResult(int requestCode, Intent data) {
        super.parseResult(requestCode, data);
        switch (requestCode) {
            case REQUEST_EXPERT: {
                ItemExport item = data.getParcelableExtra(Intents.EXPORT);
                expertId = item.getUserId();
                ((ShareContract.View) view).setExpert(item);
            }
            break;
            case REQUEST_TYPE: {
                CateBean item = data.getParcelableExtra(Intents.CATE);
                typeId = item.getCateId();
                ((ShareContract.View) view).setType(item.getCateName());
            }
            break;
        }
    }
}
