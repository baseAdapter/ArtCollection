package com.tsutsuku.artcollection.other.rent.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.model.ItemProduct;
import com.tsutsuku.artcollection.other.rent.contract.RentListContract;
import com.tsutsuku.artcollection.other.rent.model.RentListModelImpl;
import com.tsutsuku.artcollection.other.rent.presenter.RentListPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author Tsutsuku
 * @Create 2017/6/19
 * @Description
 */

public class RentListFragment extends BaseFragment implements RentListContract.View {
    private static final String IS_TOP = "isTop";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    Unbinder unbinder;

    private BaseRvAdapter<ItemProduct> adapter;
    private GridLayoutManager layoutManager;
    private RentListContract.Presenter presenter;

    public static RentListFragment newInstance(boolean isTop) {
        RentListFragment fragment = new RentListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_TOP, isTop);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rent_list;
    }

    @Override
    protected void initViews() {
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        adapter = new BaseRvAdapter<ItemProduct>(null) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new RentAdapterItem(context);
            }
        };

        presenter = new RentListPresenterImpl(new RentListModelImpl(), this);
        layoutManager = new GridLayoutManager(context, 2);

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

        rvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(5)));
        rvBase.setLayoutManager(layoutManager);
        rvBase.setAdapter(adapter);

        if (getArguments().getBoolean(IS_TOP)) {
            presenter.setTop();
        }
        presenter.getData(adapter.clearPageIndex());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void setData(int total, List<ItemProduct> list) {
        adapter.finishLoading();
        adapter.setTotal(total);
        adapter.setData(list);
    }

    @Override
    public void addData(List<ItemProduct> list) {
        adapter.addData(list);
    }
}
