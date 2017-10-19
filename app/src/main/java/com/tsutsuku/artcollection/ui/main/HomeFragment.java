package com.tsutsuku.artcollection.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.HomeContract;
import com.tsutsuku.artcollection.presenter.main.HomePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {
    Unbinder unbinder;
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private HomePresenterImpl presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        unbinder = ButterKnife.bind(this, rootView);
        presenter = new HomePresenterImpl(context);
        presenter.attachView(this);
    }

    @Override
    protected void initListeners() {
    }

    @Override
    protected void initData() {
        presenter.initView(rvBase);
        presenter.refreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tvSearch, R.id.ivType, R.id.ivMessage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearch: {
                SearchActivity.launch(context, Constants.Search.ALL);
            }
            break;
            case R.id.ivType: {
                ProductSearchActivity.launch(context);
            }
            break;
            case R.id.ivMessage: {
                MineMessageActivity.launch(context);
            }
            break;
        }
    }
}
