package com.tsutsuku.artcollection.ui.activity;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.activity.ItemActivity;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

/**
 * @Author Tsutsuku
 * @Create 2017/3/14
 * @Description
 */

public class ActivityViewAdapterItem extends BaseAdapterItem<ItemActivity>{

    @Override
    public int getLayoutResId() {
        return R.layout.item_activity_view;
    }

    @Override
    public void handleData(ItemActivity item, int position) {

    }
}
