package com.tsutsuku.artcollection.ui.lesson;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.lesson.PageListContract;
import com.tsutsuku.artcollection.presenter.PageListPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.ui.base.BasePageFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/3/5
 * @Description
 */

public class PageListFragment extends BasePageFragment implements PageListContract.View {
    private static final String CATE_ID = "cateId";

    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.srlBase)
    SwipeRefreshLayout srlBase;

    private PageListPresenterImpl presenter;
    private LinearLayoutManager layoutManager;

    public static PageListFragment newInstance(String cateId) {
        PageListFragment fragment = new PageListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATE_ID, cateId);
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
        presenter = new PageListPresenterImpl(context, getArguments().getString(CATE_ID));
        presenter.attachView(this);
    }

    @Override
    protected void initListeners() {
        srlBase.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData(true);
            }
        });

        presenter.getAdapter().setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                presenter.getData(false);
            }
        });
    }

    @Override
    protected void initData() {
        forceUpdate = true;
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBase.setLayoutManager(layoutManager);
        rvBase.setAdapter(presenter.getAdapter());
    }

    @Override
    public void hideProgress() {
        srlBase.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        srlBase.setRefreshing(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
