package com.tsutsuku.artcollection.other.custom.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.other.custom.contract.CustomHomeContract;
import com.tsutsuku.artcollection.other.custom.model.CustomHomeModelImpl;
import com.tsutsuku.artcollection.other.custom.model.ItemCustomType;
import com.tsutsuku.artcollection.other.custom.presenter.CustomHomePresenterImpl;
import com.tsutsuku.artcollection.other.rent.ui.RentListFragment;
import com.tsutsuku.artcollection.presenter.main.ADRepository;
import com.tsutsuku.artcollection.ui.BannerHolder.ADImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.UIUtils.ActivityUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/6/19
 * @Description
 */

public class CustomHomeActivity extends BaseFragmentActivity implements CustomHomeContract.View {
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.flBase)
    FrameLayout flBase;

    private GridLayoutManager layoutManager;
    private BaseRvAdapter<ItemCustomType> adapter;
    private CustomHomeContract.Presenter presenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CustomHomeActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_custom_home);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        initTitle("个性定制");

        layoutManager = new GridLayoutManager(context, 2);
        rvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(4)));

        presenter = new CustomHomePresenterImpl(this, new CustomHomeModelImpl());

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        adapter = new BaseRvAdapter<ItemCustomType>(null) {
            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new CustomTypeAdapterItem();
            }
        };

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), RentListFragment.newInstance(true), R.id.flBase);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setBar(final List<HomeBean.ADBean> list) {
        cbBase.setPages(
                new CBViewHolderCreator<ADImageHolder>() {
                    @Override
                    public ADImageHolder createHolder() {
                        return new ADImageHolder();
                    }
                }, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.icon_dot, R.drawable.icon_dot_selected})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ADRepository.parseAD(context, list.get(position));
                    }
                })
                .startTurning(4000);
    }

    @Override
    public void setCustomList(List<ItemCustomType> list) {
        rvBase.setLayoutManager(layoutManager);
        adapter.setData(list);
        rvBase.setAdapter(adapter);
    }
}
