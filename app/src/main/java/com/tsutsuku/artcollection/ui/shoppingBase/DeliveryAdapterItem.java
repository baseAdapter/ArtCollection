package com.tsutsuku.artcollection.ui.shoppingBase;

import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/2/12
 * @Description
 */

public class DeliveryAdapterItem extends BaseAdapterItem<ItemVendor.InfoBean.DeliveryBean> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.cbBase)
    CheckBox cbBase;
    @BindView(R.id.flBase)
    FrameLayout flBase;

    private OnItemSimpleClickListener listener;

    public DeliveryAdapterItem(OnItemSimpleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_delivery;
    }

    @Override
    public void handleData(ItemVendor.InfoBean.DeliveryBean item, int position) {
        tvName.setText(item.getDes() + " ¥" + item.getFee());
        cbBase.setChecked(item.isCheck());
    }

    @OnClick(R.id.flBase)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
