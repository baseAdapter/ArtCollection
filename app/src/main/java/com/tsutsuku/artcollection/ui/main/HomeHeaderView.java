package com.tsutsuku.artcollection.ui.main;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.presenter.main.ADRepository;
import com.tsutsuku.artcollection.ui.BannerHolder.ADImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/24
 * @Description 主页顶部
 */

public class HomeHeaderView {
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    @BindView(R.id.ibMenu)
    ImageButton ibMenu;
    @BindView(R.id.ibOne)
    ImageButton ibOne;
    @BindView(R.id.ibTwo)
    ImageButton ibTwo;
    @BindView(R.id.ibThree)
    ImageButton ibThree;

    private View headerView;
    private Context context;
    private BaseRvAdapter menuAdapter;
    private HomeBean homeBean;

    public HomeHeaderView(Context context, BaseRvAdapter menuAdapter) {
        this.context = context;
        this.menuAdapter = menuAdapter;
    }

    public View getView() {
        if (headerView == null) {
            headerView = LayoutInflater.from(context).inflate(R.layout.view_home, null);
            ButterKnife.bind(this, headerView);

            GridLayoutManager layoutManager = new GridLayoutManager(context, 5);
            rvMenu.setLayoutManager(layoutManager);

            rvMenu.setAdapter(menuAdapter);
        }
        return headerView;
    }

    public void setData(final HomeBean bean) {
        homeBean = bean;

        cbBase.setPages(
                new CBViewHolderCreator<ADImageHolder>() {
                    @Override
                    public ADImageHolder createHolder() {
                        return new ADImageHolder();
                    }
                }, bean.getTopbanner())
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.icon_dot, R.drawable.icon_dot_selected})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ADRepository.parseAD(context, bean.getTopbanner().get(position));
                    }
                })
                .startTurning(4000);

        menuAdapter.getData().clear();
        menuAdapter.getData().addAll(bean.getMenus());
        menuAdapter.notifyDataSetChanged();


        Glide.with(context).load(bean.getMenubutton().get(0).getPic()).into(ibMenu);
        Glide.with(context).load(bean.getTop3().get(0).getPic()).into(ibOne);
        Glide.with(context).load(bean.getTop3().get(1).getPic()).into(ibTwo);
        Glide.with(context).load(bean.getTop3().get(2).getPic()).into(ibThree);
    }

    @OnClick({R.id.ibMenu, R.id.ibOne, R.id.ibTwo, R.id.ibThree})
    public void onViewClicked(View view) {
        if (homeBean == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.ibMenu:
                ADRepository.parseAD(context, homeBean.getMenubutton().get(0));
                break;
            case R.id.ibOne:
                ADRepository.parseAD(context, homeBean.getTop3().get(0));
                break;
            case R.id.ibTwo:
                ADRepository.parseAD(context, homeBean.getTop3().get(1));
                break;
            case R.id.ibThree:
                ADRepository.parseAD(context, homeBean.getTop3().get(2));
                break;
        }
    }
}
