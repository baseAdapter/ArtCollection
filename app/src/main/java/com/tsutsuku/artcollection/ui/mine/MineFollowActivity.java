package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemFollow;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.shopping.VendorDetailActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/3/2
 * @Description 我的关注页面
 */

public class MineFollowActivity extends BaseActivity {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private BaseRvAdapter adapter;
    private List<ItemFollow> list;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MineFollowActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_follow);
    }

    @Override
    public void initViews() {
        initTitle(R.string.mine_follow);
        ButterKnife.bind(this);

    }

    @Override
    public void initListeners() {
        rvBase.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        adapter = new BaseRvAdapter<ItemFollow>(list) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new FollowAdapterItem(context, new OnItemClickListener<ItemFollow>() {
                    @Override
                    public void onItemClick(ItemFollow item) {
                        VendorDetailActivity.launch(context, item.getId());
                    }

                    @Override
                    public void onItemLongClick(ItemFollow item) {
                        showDialog(item);
                    }
                });
            }
        };

        rvBase.setAdapter(adapter);
        getData();
    }

    private void showDialog(final ItemFollow item) {
        new MaterialDialog.Builder(context)
                .title("提示")
                .content("确认取消关注" + item.getFarmName() + "?")
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        unFollow(item);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                }).build()
                .show();

    }

    public void unFollow(final ItemFollow item) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Follow.delete");
        hashMap.put("pId", item.getId());
        hashMap.put("ctype", "1");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    list.remove(item);
                    adapter.notifyItemRemoved(list.indexOf(item));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Follow.get");
        hashMap.put("ctype", "1");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    list.addAll(GsonUtils.parseJsonArray(data.getString("list"), ItemFollow.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}
