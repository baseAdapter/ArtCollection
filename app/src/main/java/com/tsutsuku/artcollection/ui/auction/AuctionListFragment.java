package com.tsutsuku.artcollection.ui.auction;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.AuctionListContract;
import com.tsutsuku.artcollection.presenter.AuctionListPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/3/19
 * @Description 参拍记录Fragment
 */

public class AuctionListFragment extends BaseFragment implements AuctionListContract.View {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private AuctionListPresenterImpl presenter;
    private LinearLayoutManager layoutManager;

    public static AuctionListFragment newInstance() {
        AuctionListFragment fragment = new AuctionListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_rv;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        presenter = new AuctionListPresenterImpl();
        presenter.attachView(this);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBase.setLayoutManager(layoutManager);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        rvBase.setAdapter(presenter.getAdapter());
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
        presenter.getData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
