package com.tsutsuku.artcollection.ui.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.contract.circle.CircleModuleContract;
import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.presenter.circle.CircleModulePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.RxBus;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author Sun Renwei
 * @Create 2016/10/30
 * @Description 动态列表Fragment
 */

public class CircleModuleFragment extends BaseFragment implements CircleModuleContract.View {
    @BindView(R.id.rvCircle)
    RecyclerView rvCircle;
    @BindView(R.id.srlCircle)
    SwipeRefreshLayout srlCircle;
    @BindView(R.id.etReplay)
    EditText etReplay;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.llReplay)
    LinearLayout llReplay;

    private LinearLayoutManager layoutManager;
    private Observable<BusBean> observable;

    private CircleModulePresenterImpl presenter;

    public static CircleModuleFragment newInstance(Bundle bundle) {
        CircleModuleFragment circleFragment = new CircleModuleFragment();
        circleFragment.setArguments(bundle);
        return circleFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle_module;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        presenter = new CircleModulePresenterImpl();
        presenter.attachView(this);
    }

    @Override
    protected void initListeners() {
        observable = RxBus.getDefault().register(BusEvent.CIRCLE, BusBean.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BusBean>() {
            @Override
            public void call(BusBean bean) {
                presenter.parseBusEvent(bean);
            }
        });

        srlCircle.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadCircle(true);
            }
        });
        presenter.getAdapter().setupScroll(rvCircle, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                presenter.loadCircle(false);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                presenter.replay();
            }
        });
    }

    @Override
    protected void initData() {
        presenter.parseIntent(getArguments());

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCircle.setLayoutManager(layoutManager);
        rvCircle.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(5))
                .color(getResources().getColor(R.color.transparent))
                .build());

        rvCircle.setAdapter(presenter.getAdapter());
        presenter.loadCircle(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(BusEvent.CIRCLE, observable);
        presenter.detachView();
    }

    @Override
    public void showProgress() {
        srlCircle.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlCircle.setRefreshing(false);
    }

    @Override
    public Context getmContext() {
        return context;
    }
}
