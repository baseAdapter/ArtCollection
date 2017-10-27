package com.tsutsuku.artcollection.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.CateBean;
import com.tsutsuku.artcollection.model.ItemProduct;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.common.CateSelectActivity;
import com.tsutsuku.artcollection.ui.mine.FeedbackActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;
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

public class ProductSearchActivity extends BaseActivity {

    public static final String TAG = ProductSearchActivity.class.getSimpleName();


    private static final int CATE = 0;
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.tvOpinion)
    TextView tvOpinion;

    private BaseRvAdapter adapter;
    private ArrayList<ItemProduct> list;
    private LinearLayoutManager layoutManager;

    private String spcateId = "";

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductSearchActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_product_search);
    }

    @Override
    public void initViews() {
        initTitle("搜索", R.drawable.icon_type);
        ButterKnife.bind(this);
        layoutManager = new GridLayoutManager(context, 2);
        rvBase.setLayoutManager(layoutManager);

        rvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(3)));
        rvBase.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(3))
                .colorResId(R.color.transparent)
                .build());

        SpannableStringBuilder builder = new SpannableStringBuilder("抱歉,该分类下暂无宝贝，\n您可以告诉我们，我们将尽快改进");
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 16, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                FeedbackActivity.launch(context);
            }
        }, 16, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvOpinion.setText(builder);
        tvOpinion.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {

        list = new ArrayList<>();
        adapter = new BaseRvAdapter<ItemProduct>(list) {

            @Override
            public Object getItemType(ItemProduct itemProduct) {
                return itemProduct.getIsAuction();
            }

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                switch ((int) type) {
                    case 0:
                        return new ProductAdapterItem(context);
                    default:
                        return new AuctionAdapterItem(context);
                }
            }
        };
        adapter.setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                getData(false, spcateId);
            }
        });
        rvBase.setAdapter(adapter);

        CateSelectActivity.launchTypeDetail(this, CATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            getData(true, ((CateBean) data.getParcelableExtra(Intents.CATE)).getCateId());
        } else if (resultCode == RESULT_CANCELED) {
            finish();
        }
    }

    private void getData(final boolean refresh, String spacteId) {
        this.spcateId = spacteId;

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getProductsList");
        hashMap.put("spcateId", spacteId);
        hashMap.put("pageIndex", refresh ? adapter.clearPageIndex() : adapter.addPageIndex());
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (refresh) {
                    adapter.setTotal(data.getJSONObject("list").getInt("total"));
                    list.clear();

                    tvOpinion.setVisibility(data.getJSONObject("list").getInt("total") == 0 ? View.VISIBLE : View.GONE);
                }
                list.addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemProduct.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @OnClick(R.id.ivTitleButton)
    public void onViewClicked() {
        CateSelectActivity.launchTypeDetail(this, CATE);
    }
}
