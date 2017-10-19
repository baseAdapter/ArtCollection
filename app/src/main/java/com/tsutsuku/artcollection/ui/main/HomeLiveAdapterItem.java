package com.tsutsuku.artcollection.ui.main;

import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.activity.ItemActivity;
import com.tsutsuku.artcollection.ui.activity.ActivityDetailActivity;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/5/3
 * @Description
 */

public class HomeLiveAdapterItem extends BaseAdapterItem<ItemActivity> {
    @BindView(R.id.ibPic)
    ImageButton ibPic;

    @Override
    public int getLayoutResId() {
        return R.layout.item_home_live;
    }

    @Override
    public void handleData(ItemActivity item, int position) {
        Glide.with(context).load(item.getCoverPhoto()).into(ibPic);
    }

    @OnClick(R.id.ibPic)
    public void onViewClicked() {
        ActivityDetailActivity.launch(context, curItem.getActivityId());
    }
}
