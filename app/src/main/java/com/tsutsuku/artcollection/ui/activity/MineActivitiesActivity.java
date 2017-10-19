package com.tsutsuku.artcollection.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.activity.ItemActivity;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/3/14
 * @Description
 */

public class MineActivitiesActivity extends BaseActivity {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MineActivitiesActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_activities);
    }

    @Override
    public void initViews() {
        initTitle(R.string.mine_activities);
        ButterKnife.bind(this);

        rvBase.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        getData();
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.getMyActivitieList");
        hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    rvBase.setAdapter(new BaseRvAdapter<ItemActivity>(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemActivity.class)) {

                        @NonNull
                        @Override
                        public AdapterItem createItem(@NonNull Object type) {
                            return new ActivityAdapterItem(context, new OnItemSimpleClickListener<ItemActivity>() {
                                @Override
                                public void onItemClick(ItemActivity item) {
                                    ActivityDetailActivity.launch(context, item.getActivityId());
                                }
                            });
                        }
                    });
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}
