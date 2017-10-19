package com.tsutsuku.artcollection.ui.collection;

import android.os.Bundle;
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
import com.tsutsuku.artcollection.model.collection.ItemCollection;
import com.tsutsuku.artcollection.other.rent.ui.RentDetailActivity;
import com.tsutsuku.artcollection.ui.base.BasePageFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.lesson.LessonDetailActivity;
import com.tsutsuku.artcollection.ui.product.ProductDetailActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/3/25
 * @Description
 */

public class CollectionPageFragment extends BasePageFragment {
    private static final String CTYPE = "ctype";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private BaseRvAdapter<ItemCollection> adapter;

    public static CollectionPageFragment newInstance(String ctype) {
        CollectionPageFragment fragment = new CollectionPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CTYPE, ctype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void fetchData() {
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_rv;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);

        rvBase.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Collections.get");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("ctype", getArguments().getString(CTYPE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    adapter = new BaseRvAdapter<ItemCollection>(GsonUtils.parseJsonArray(data.getString("list"), ItemCollection.class)) {
                        @NonNull
                        @Override
                        public AdapterItem createItem(@NonNull Object type) {
                            return new CollectionAdapterItem(new OnItemClickListener<ItemCollection>() {
                                @Override
                                public void onItemClick(ItemCollection item) {
                                    switch (item.getCtype()) {
                                        case "1": {
                                            LessonDetailActivity.launch(context, item.getId());
                                        }
                                        break;
                                        case "2": {
                                            if ("2".equals(item.getIsAuction())) {
                                                RentDetailActivity.launch(context, item.getId());
                                            } else {
                                                ProductDetailActivity.launch(context, item.getId());
                                            }
                                        }
                                        break;
                                    }
                                }

                                @Override
                                public void onItemLongClick(final ItemCollection item) {
                                    new MaterialDialog.Builder(context)
                                            .title("提示")
                                            .content("确认取消收藏？")
                                            .positiveText(R.string.ok)
                                            .negativeText(R.string.cancel)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    cancelCollection(item);
                                                }
                                            }).show();
                                }
                            });
                        }
                    };
                    rvBase.setAdapter(adapter);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void cancelCollection(final ItemCollection item) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Collections.delete");
        hashMap.put("ctype", item.getCtype());
        hashMap.put("pId", item.getId());
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    int position = adapter.getData().indexOf(item);
                    adapter.getData().remove(item);
                    adapter.notifyItemRemoved(position);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}
