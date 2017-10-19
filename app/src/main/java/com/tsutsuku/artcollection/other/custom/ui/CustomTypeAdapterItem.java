package com.tsutsuku.artcollection.other.custom.ui;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.other.custom.model.ItemCustomType;
import com.tsutsuku.artcollection.other.rent.ui.RentListActivity;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/6/20
 * @Description
 */

public class CustomTypeAdapterItem extends BaseAdapterItem<ItemCustomType> {
    @BindView(R.id.ivPic)
    ImageView ivPic;

    @Override
    public int getLayoutResId() {
        return R.layout.item_custom_type;
    }

    @Override
    public void handleData(ItemCustomType item, int position) {
        Glide.with(context).load(item.getPhoto()).into(ivPic);
    }

    @OnClick(R.id.ivPic)
    public void onViewClicked() {
        if (Integer.valueOf(curItem.getCateId()) < 0){
            RentListActivity.launch(context);
        } else {
            CustomListActivity.launch(context, curItem.getCateId(), curItem.getCateName());
        }

    }
}
