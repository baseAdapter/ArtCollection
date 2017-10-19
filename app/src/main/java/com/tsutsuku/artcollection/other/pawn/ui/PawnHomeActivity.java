package com.tsutsuku.artcollection.other.pawn.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.other.pawn.contract.PawnHomeContract;
import com.tsutsuku.artcollection.other.pawn.model.ItemCrowd;
import com.tsutsuku.artcollection.other.pawn.model.PawnHomeModelImpl;
import com.tsutsuku.artcollection.other.pawn.presenter.PawnHomePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.utils.RcvAdapterWrapper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/6/21
 * @Description
 */

public class PawnHomeActivity extends BaseActivity implements PawnHomeContract.View {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private BaseRvAdapter adapter;
    private LinearLayoutManager layoutManager;
    private PawnHomeContract.Presenter presenter;
    private PawnHomeView headerView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PawnHomeActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_fans);
    }

    @Override
    public void initViews() {
        initTitle("典当");
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        presenter = new PawnHomePresenterImpl(this, new PawnHomeModelImpl());

        adapter = new BaseRvAdapter<ItemCrowd>(null) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new CrowdAdapterItem();
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
        headerView = new PawnHomeView(context);
        rvBase.setLayoutManager(layoutManager);
        RcvAdapterWrapper wrapper = new RcvAdapterWrapper(adapter, layoutManager);
        wrapper.setHeaderView(headerView.getView());

        rvBase.setLayoutManager(layoutManager);
        rvBase.setAdapter(wrapper);

        presenter.getData(adapter.clearPageIndex());
        presenter.getTop();
    }

    @Override
    public void setData(int total, List<ItemCrowd> list) {
        adapter.finishLoading();
        adapter.setTotal(total);
        adapter.setData(list);
    }

    @Override
    public void addData(List<ItemCrowd> list) {
        adapter.finishLoading();
        adapter.addData(list);
    }

    @Override
    public void setTop(List<HomeBean.ADBean> list) {
        headerView.setData(list);
    }
}
