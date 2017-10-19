package com.tsutsuku.artcollection.other.custom.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.other.custom.contract.CustomListContract;
import com.tsutsuku.artcollection.other.custom.model.CustomListModelImpl;
import com.tsutsuku.artcollection.other.custom.model.ItemCustomBean;
import com.tsutsuku.artcollection.other.custom.presenter.CustomListPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/6/8
 * @Description
 */

public class CustomListActivity extends BaseActivity implements CustomListContract.View {
    private static final String CATE_ID = "cateId";
    private static final String CATE_NAME = "cateName";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private BaseRvAdapter<ItemCustomBean> adapter;
    private LinearLayoutManager layoutManager;
    private CustomListContract.Presenter presenter;

    public static void launch(Context context, String cateId, String cateName) {
        context.startActivity(new Intent(context, CustomListActivity.class)
                .putExtra(CATE_ID, cateId)
                .putExtra(CATE_NAME, cateName));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_fans);
    }

    @Override
    public void initViews() {
        initTitle(getIntent().getStringExtra(CATE_NAME));
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        presenter = new CustomListPresenterImpl(this, new CustomListModelImpl());

        adapter = new BaseRvAdapter<ItemCustomBean>(null) {
            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new CustomAdapterItem();
            }
        };

        adapter.setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                presenter.getData(adapter.addPageIndex());
            }
        });
        rvBase.setAdapter(adapter);
        rvBase.setLayoutManager(layoutManager);
        rvBase.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(10))
                .build());

        presenter.setId(getIntent().getStringExtra(CATE_ID));
        presenter.getData("1");

    }


    @Override
    public void addData(List<ItemCustomBean> list) {
        adapter.addData(list);
    }

    @Override
    public void finishLoading() {
        adapter.finishLoading();
    }

    @Override
    public void setTotal(int total) {
        adapter.setTotal(total);
    }
}
