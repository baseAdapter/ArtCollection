package com.tsutsuku.artcollection.other.pawn.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.other.pawn.contract.DealerListContract;
import com.tsutsuku.artcollection.other.pawn.model.DealerListModelImpl;
import com.tsutsuku.artcollection.other.pawn.model.ItemDealer;
import com.tsutsuku.artcollection.other.pawn.presenter.DealerListPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/6/21
 * @Description
 */

public class DealerListActivity extends BaseActivity implements DealerListContract.View {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private BaseRvAdapter adapter;
    private LinearLayoutManager layoutManager;
    private DealerListContract.Presenter presenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, DealerListActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_fans);
    }

    @Override
    public void initViews() {
        initTitle("艺术经纪人");
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBase.setLayoutManager(layoutManager);
        rvBase.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(5))
                .build());
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        adapter = new BaseRvAdapter<ItemDealer>(null) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new DealerAdapterItem();
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
        rvBase.setAdapter(adapter);

        presenter = new DealerListPresenterImpl(this, new DealerListModelImpl());
        presenter.getData(adapter.clearPageIndex());
    }

    @Override
    public void setData(int total, List<ItemDealer> list) {
        adapter.finishLoading();
        adapter.setTotal(total);
        adapter.setData(list);
    }

    @Override
    public void addData(List<ItemDealer> list) {
        adapter.finishLoading();
        adapter.addData(list);
    }
}
