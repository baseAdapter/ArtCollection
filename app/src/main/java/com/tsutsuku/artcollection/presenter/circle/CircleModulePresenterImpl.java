package com.tsutsuku.artcollection.presenter.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseBooleanArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.circle.CircleModuleContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.model.ItemCircle;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.circle.CircleAdapterItem;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/01/06
 */

public class CircleModulePresenterImpl implements CircleModuleContract.Presenter {
    private static final String TYPE_TREASURE = "0";
    private static final String TYPE_IDENTIFY = "1";
    private static final String TYPE_MINE = "2";

    private static final String TYPE = "type";
    private static final String SPCATE_ID = "spcateId";
    private static final String IS_FREE = "isFree";
    private static final String IS_MINE = "isMine";

    private CircleModuleContract.View view;

    private Gson gson = new Gson();
    private ArrayList<ItemCircle> list = new ArrayList<>();
    private Type circleType = new TypeToken<List<ItemCircle>>() {
    }.getType();

    private String lastId = "";
    private String type = "0";
    private String spcateId = "";
    private boolean isMine;
    private boolean isFree;

    private SparseBooleanArray mCollapsedStatus = new SparseBooleanArray();

    private BaseRvAdapter adapter;

    public static Bundle createTypeTreasure(boolean isMine) {
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, TYPE_TREASURE);
        bundle.putBoolean(IS_MINE, isMine);
        return bundle;
    }

    public static Bundle createTypeIdentify(boolean isFree) {
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, TYPE_IDENTIFY);
        bundle.putBoolean(IS_FREE, isFree);
        return bundle;
    }


    @Override
    public void attachView(CircleModuleContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void parseIntent(Bundle bundle) {
        type = bundle.getString(TYPE);
        isMine = bundle.getBoolean(IS_MINE);
        isFree = bundle.getBoolean(IS_FREE);
    }

    @Override
    public void replay(String content, int msgId, boolean isComment) {

    }

    @Override
    public void loadCircle(boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.getMessages");
        String userId = SharedPref.getString(Constants.USER_ID);
        hashMap.put("user_id", TextUtils.isEmpty(userId) ? "0" : userId);

        if (isMine) {
            hashMap.put("ctype", TYPE_MINE);
        } else {
            hashMap.put("ctype", type);
            if (type.equals(TYPE_IDENTIFY)) {
                hashMap.put("isFree", isFree ? "1" : "2");
            }
        }

        if (refresh) {
            lastId = "";
        }
        hashMap.put("lastId", lastId);

        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if ("".equals(lastId)) {
                        list.clear();
                    }
                    list.addAll((List<ItemCircle>) gson.fromJson(data.getString("list"), circleType));
                }
                lastId = list.size() > 0 ? list.get(list.size() - 1).getMsgId() : "0";
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }

            @Override

            protected void onFinish() {
                view.hideProgress();
            }
        });
    }

    /**
     * @return
     */
    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemCircle>(list) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object item) {
                    return new CircleAdapterItem(mCollapsedStatus);
                }
            };
        }
        return adapter;
    }

    private void delete(String msgId) {
        for (ItemCircle item : list) {
            if (msgId.equals(item.getMsgId())) {
                int index = list.indexOf(item);
                list.remove(index);
                adapter.notifyItemRemoved(index);
                return;
            }
        }
    }

    @Override
    public void parseBusEvent(BusBean bean) {
        if (bean.getType() == Integer.valueOf(type)) {
            loadCircle(true);
        } else if (bean.getType() == Constants.CIRCLE_DELETE) {
            delete(bean.getData());
        }
    }
}