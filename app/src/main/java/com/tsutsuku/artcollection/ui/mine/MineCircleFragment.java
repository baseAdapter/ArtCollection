package com.tsutsuku.artcollection.ui.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.MineCircleContract;
import com.tsutsuku.artcollection.presenter.circle.MineCirclePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/3/12
 * @Description
 */

public class MineCircleFragment extends BaseFragment implements MineCircleContract.View {
    public static final String CTYPE = "ctype";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.srlBase)
    SwipeRefreshLayout srlBase;

    private MineCirclePresenterImpl presenter;
    public static MineCircleFragment newInstance(String ctype) {
        MineCircleFragment fragment = new MineCircleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CTYPE, ctype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_rv;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        presenter = new MineCirclePresenterImpl(context, getArguments().getString(CTYPE));
        presenter.attachView(this);

        rvBase.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(5))
                .color(getResources().getColor(R.color.transparent))
                .build());


    }

    @Override
    protected void initListeners() {
        srlBase.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData(true);
            }
        });
    }

    @Override
    protected void initData() {
        rvBase.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvBase.setAdapter(presenter.getAdapter());
        presenter.getData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void hideProgress() {
        srlBase.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        srlBase.setRefreshing(true);
    }
}
