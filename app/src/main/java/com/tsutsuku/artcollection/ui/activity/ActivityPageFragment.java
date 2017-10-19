package com.tsutsuku.artcollection.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.activity.ActivityPageContract;
import com.tsutsuku.artcollection.presenter.activity.ActivityPagePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BasePageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/3/12
 * @Description
 */

public class ActivityPageFragment extends BasePageFragment implements ActivityPageContract.View {
    public static final String CTYPE = "ctype";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.srlBase)
    SwipeRefreshLayout srlBase;
    private ActivityPagePresenterImpl presenter;

    public static ActivityPageFragment newInstance(String ctype) {
        ActivityPageFragment fragment = new ActivityPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CTYPE, ctype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void fetchData() {
        presenter.getData(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_rv;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        presenter = new ActivityPagePresenterImpl(context, getArguments().getString(CTYPE));
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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
    public void showProgress() {
        srlBase.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlBase.setRefreshing(false);
    }
}
