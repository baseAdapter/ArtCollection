package com.tsutsuku.artcollection.ui.product;

import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.presenter.main.ADRepository;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/5/4
 * @Description
 */

public class ProductADAdapterItem extends BaseAdapterItem<HomeBean.ADBean> {
    @BindView(R.id.ivPic)
    ImageView ivPic;

    @Override
    public int getLayoutResId() {
        return R.layout.item_product_ad;
    }

    @Override
    public void handleData(HomeBean.ADBean item, int position) {
        Glide.with(context).load(item.getPic()).into(ivPic);
    }

    @OnClick(R.id.ivPic)
    public void onViewClicked() {
        ADRepository.parseAD(context, curItem);
    }
}
