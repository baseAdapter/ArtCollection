package com.tsutsuku.artcollection.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemSearch;
import com.tsutsuku.artcollection.other.rent.ui.RentDetailActivity;
import com.tsutsuku.artcollection.ui.activity.ActivityDetailActivity;
import com.tsutsuku.artcollection.ui.auction.AuctionDetailActivity;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.ui.lesson.LessonDetailActivity;
import com.tsutsuku.artcollection.ui.product.ProductDetailActivity;
import com.tsutsuku.artcollection.ui.shopping.VendorDetailActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.KeyBoardUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/4/3
 * @Description
 */

public class SearchActivity extends BaseActivity {
    private static final String TYPE = "type";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;

    private BaseRvAdapter adapter;
    private ArrayList<ItemSearch> list;

    public static void launch(Context context, String type) {
        context.startActivity(new Intent(context, SearchActivity.class)
                .putExtra(TYPE, type));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        rvBase.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvBase.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(5))
                .build());

        switch (getIntent().getStringExtra(TYPE)) {
            case Constants.Search.ALL: {
                etSearch.setHint(R.string.search_blank);
            }
            break;
            case Constants.Search.VENDOR: {
                etSearch.setHint("请输入关键字搜索商家");
            }
            break;
            case Constants.Search.GOODS: {
                etSearch.setHint("请输入关键字搜索商品");
            }
            break;
            case Constants.Search.ACTIVITY: {
                etSearch.setHint("请输入关键字搜索活动");
            }
            break;
            case Constants.Search.LESSON: {
                etSearch.setHint("请输入关键字搜索讲堂");
            }
            break;
            case Constants.Search.LIVE: {
                etSearch.setHint("请输入关键字搜索直播");
            }
            break;
            case Constants.Search.NEWS: {
                etSearch.setHint("请输入关键字搜索资讯");
            }
            break;
        }
    }

    @Override
    public void initListeners() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getData(etSearch.getText().toString().trim());
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        adapter = new BaseRvAdapter<ItemSearch>(list) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new SearchAdapterItem(new OnItemSimpleClickListener<ItemSearch>() {
                    @Override
                    public void onItemClick(ItemSearch item) {
                        switch (item.getType()) {
                            case Constants.Search.VENDOR: {
                                VendorDetailActivity.launch(context, item.getItemId());
                            }
                            break;
                            case Constants.Search.GOODS: {
                                if ("0".equals(item.getOtherinfo())) {
                                    ProductDetailActivity.launch(context, item.getItemId());
                                } else if ("1".equals(item.getOtherinfo())){
                                    AuctionDetailActivity.launch(context, item.getItemId());
                                } else {
                                    RentDetailActivity.launch(context, item.getItemId());
                                }
                            }
                            break;
                            case Constants.Search.LESSON: {
                                LessonDetailActivity.launch(context, item.getItemId());
                            }
                            break;
                            case Constants.Search.NEWS:{
                                WebActivity.launch(context, item.getOtherinfo(), item.getProductName());
                            }
                            default: {
                                ActivityDetailActivity.launch(context, item.getItemId());
                            }
                            break;
                        }
                    }
                });
            }
        };

        rvBase.setAdapter(adapter);
    }

    private void getData(String keyword) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.searchInfo");
        hashMap.put("ctype", getIntent().getStringExtra(TYPE));
        hashMap.put("keyWord", keyword);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    list.clear();
                    list.addAll(GsonUtils.parseJsonArray(data.getString("list"), ItemSearch.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @OnClick({R.id.llBack, R.id.tvSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBack:
                finish();
                KeyBoardUtils.closeKeyboard(this);
                break;
            case R.id.tvSearch:
                getData(etSearch.getText().toString().trim());
                break;
        }
    }
}
