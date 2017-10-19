package com.tsutsuku.artcollection.ui.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemExport;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/2/28
 * @Description 选择鉴定专家列表
 */

public class ExportListActivity extends BaseActivity {
    private static final String IS_FREE = "isFree";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private BaseRvAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<ItemExport> list;

    public static void launch(Activity activity, int requestCode, boolean isFree) {
        activity.startActivityForResult(new Intent(activity, ExportListActivity.class).putExtra(IS_FREE, isFree), requestCode);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_export_list);
    }

    @Override
    public void initViews() {
        initTitle(R.string.export);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBase.setLayoutManager(layoutManager);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        list = new ArrayList<>();

        adapter = new BaseRvAdapter<ItemExport>(list) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new ExportAdapterItem(new OnItemSimpleClickListener<ItemExport>() {
                    @Override
                    public void onItemClick(ItemExport item) {
                        setResult(RESULT_OK, new Intent().putExtra(Intents.EXPORT, item));
                        finish();
                    }
                });
            }
        };
        adapter.setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                getData(false);
            }
        });
        rvBase.setAdapter(adapter);

        getData(true);
    }

    private void getData(final boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.getJianDingUserList");
        hashMap.put("isFree", getIntent().getBooleanExtra(IS_FREE, false) ? "1" : "0");
        hashMap.put("pageIndex", refresh ? adapter.clearPageIndex() : adapter.addPageIndex());
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if (refresh) {
                        adapter.setTotal(data.getJSONObject("list").getInt("total"));
                    }
                    list.addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemExport.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}
