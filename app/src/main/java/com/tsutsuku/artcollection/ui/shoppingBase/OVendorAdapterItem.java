package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.shopping.ItemOrder;
import com.tsutsuku.artcollection.presenter.shopping.ShoppingRepository;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/2/11
 * @Description
 */

public class OVendorAdapterItem extends BaseAdapterItem<ItemOrder> {
    @BindView(R.id.tvVendor)
    TextView tvVendor;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.llVendor)
    LinearLayout lllVendor;

    private OnItemSimpleClickListener listener;

    public OVendorAdapterItem(Context context, OnItemSimpleClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vendor_view;
    }

    @Override
    public void handleData(ItemOrder item, int position) {
        tvVendor.setText(item.getFarmName());
        tvStatus.setText(ShoppingRepository.getOrderStatus(item.getOrderStatus(), item.getDeliveryStatus()));
    }

    @Override
    public void handleData(ItemOrder item, int position, List<Object> payloads) {
        tvStatus.setText(ShoppingRepository.getOrderStatus(item.getOrderStatus(), item.getDeliveryStatus()));
    }

    @OnClick(R.id.llVendor)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
