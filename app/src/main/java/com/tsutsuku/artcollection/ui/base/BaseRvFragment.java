package com.tsutsuku.artcollection.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.BaseRvContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/1/9
 * @Description 基础RecyclerView列表Fragment
 */

public class BaseRvFragment extends BaseFragment implements BaseRvContract.View {

    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.srlBase)
    SwipeRefreshLayout srlBase;

    BaseRvContract.Presenter presenter;
    private LinearLayoutManager layoutManager;

    public static Fragment newInstance(BaseRvContract.Presenter presenter){
        BaseRvFragment rvFragment = new BaseRvFragment();
        rvFragment.setPresenter(presenter);
        return rvFragment;
    }

    public void setPresenter(BaseRvContract.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_rv;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        presenter.attachView(this);

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBase.setLayoutManager(layoutManager);
    }

    @Override
    protected void initListeners() {
        srlBase.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadCircle(true);
            }
        });

        presenter.getAdapter().setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                presenter.loadCircle(false);
            }
        });
    }

    @Override
    protected void initData() {
        rvBase.setAdapter(presenter.getAdapter());
        presenter.loadCircle(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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
