package com.tsutsuku.artcollection.ui.point;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.PointListContract;
import com.tsutsuku.artcollection.presenter.PointListPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description 积分商品列表View
 */

public class PointListActivity extends BaseActivity implements PointListContract.View {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private PointListPresenterImpl presenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PointListActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_point_list);
    }

    @Override
    public void initViews() {
        initTitle(R.string.point_exchange);
        ButterKnife.bind(this);
        presenter = new PointListPresenterImpl();
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        rvBase.setLayoutManager(layoutManager);
        rvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(3)));

        rvBase.setAdapter(presenter.getAdapter());

        presenter.getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
