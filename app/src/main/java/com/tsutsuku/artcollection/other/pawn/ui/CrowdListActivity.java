package com.tsutsuku.artcollection.other.pawn.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.other.pawn.contract.CrowListContract;
import com.tsutsuku.artcollection.other.pawn.model.CrowListModelImpl;
import com.tsutsuku.artcollection.other.pawn.model.ItemCrowdType;
import com.tsutsuku.artcollection.other.pawn.presenter.CrowListPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.utils.RcvAdapterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/6/26
 * @Description
 */

public class CrowdListActivity extends BaseActivity implements CrowListContract.View {
    private static final String ID = "id";
    private static final String PIC = "pic";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private CrowListContract.Presenter presenter;
    private BaseRvAdapter<ItemCrowdType> adapter;
    private LinearLayoutManager layoutManager;

    public static void launch(Context context, String id, List<String> list) {
        context.startActivity(new Intent(context, CrowdListActivity.class)
                .putExtra(ID, id)
                .putStringArrayListExtra(PIC, (ArrayList) list));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_crow_list);
    }

    @Override
    public void initViews() {
        initTitle("艺术品众筹", R.drawable.icon_share_xxhdpi);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        adapter = new BaseRvAdapter<ItemCrowdType>(null) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new CrowdTypeAdapterItem();
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

        presenter = new CrowListPresenterImpl(getIntent().getStringExtra(ID),
                context,
                this,
                new CrowListModelImpl(),
                getIntent().getStringArrayListExtra(PIC).get(0));
        presenter.getData(adapter.clearPageIndex());
    }

    @OnClick(R.id.ivTitleButton)
    public void onViewClicked() {
        presenter.share();
    }

    @Override
    public void setData(int total, List<ItemCrowdType> list) {
        adapter.setTotal(total);
        CrowdListView headerView = new CrowdListView(context);
        rvBase.setLayoutManager(layoutManager);
        RcvAdapterWrapper wrapper = new RcvAdapterWrapper(adapter, layoutManager);
        wrapper.setHeaderView(headerView.getView());
        headerView.setData(getIntent().getStringArrayListExtra(PIC), String.valueOf(total));

        rvBase.setLayoutManager(layoutManager);
        rvBase.setAdapter(wrapper);
        adapter.setData(list);
        adapter.finishLoading();
    }

    @Override
    public void addData(List<ItemCrowdType> list) {
        adapter.addData(list);
        adapter.finishLoading();
    }
}
